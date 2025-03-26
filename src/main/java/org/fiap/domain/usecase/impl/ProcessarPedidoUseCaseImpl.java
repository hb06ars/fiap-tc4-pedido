package org.fiap.domain.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.fiap.app.rest.request.estoque.EstoqueRequest;
import org.fiap.app.service.ClienteGatewayService;
import org.fiap.app.service.EstoqueGatewayService;
import org.fiap.app.service.ProdutoGatewayService;
import org.fiap.domain.dto.ClienteDTO;
import org.fiap.domain.dto.PedidoDTO;
import org.fiap.domain.dto.ProdutoDTO;
import org.fiap.domain.usecase.ProcessarPedidoUseCase;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Slf4j
public class ProcessarPedidoUseCaseImpl implements ProcessarPedidoUseCase {

    private final ClienteGatewayService clienteGatewayService;
    private final ProdutoGatewayService produtoGatewayService;
    private final EstoqueGatewayService estoqueGatewayService;

    public ProcessarPedidoUseCaseImpl(ClienteGatewayService clienteGatewayService, ProdutoGatewayService produtoGatewayService, EstoqueGatewayService estoqueGatewayService) {
        this.clienteGatewayService = clienteGatewayService;
        this.produtoGatewayService = produtoGatewayService;
        this.estoqueGatewayService = estoqueGatewayService;
    }

    @Override
    public void execute(PedidoDTO pedidoDTO) {
        ClienteDTO cliente = clienteGatewayService.findById(pedidoDTO.getClienteId());
        List<ProdutoDTO> produtos = buscarProdutos(pedidoDTO);
        BigDecimal totalCompra = calcularTotal(pedidoDTO, produtos);
        preenchendoProdutoId(produtos, pedidoDTO);

        if (baixaEstoqueEfetuada(pedidoDTO)) {
            log.info("SALVAR PEDIDO E ITENS DO PEDIDO.");
        }

    }

    private boolean baixaEstoqueEfetuada(PedidoDTO pedidoDTO) {
        if (validarEstoque(pedidoDTO)) {
            efetuarBaixa(pedidoDTO);
            log.info("Baixa de Estoque relizada com sucesso.");
            return true;
        }
        return false;
    }

    private void efetuarBaixa(PedidoDTO pedidoDTO) {
        pedidoDTO.getItensPedidoList().forEach(item ->
                estoqueGatewayService.updateByIdProduto(
                        EstoqueRequest.builder()
                                .id(item.getProdutoId())
                                .quantidade(item.getQuantidade())
                                .build()
                ));
        log.info("Baixa de Estoque em execução.");
    }

    private boolean validarEstoque(PedidoDTO pedidoDTO) {
        var estoqueInvalido = pedidoDTO.getItensPedidoList()
                .stream()
                .anyMatch(item -> item.getQuantidade() < 0);

        if (estoqueInvalido) {
            log.error("Estoque inválido.");
            return false;
        }
        log.info("Estoque válido.");
        return true;
    }

    private List<ProdutoDTO> buscarProdutos(PedidoDTO pedidoDTO) {
        return pedidoDTO.getItensPedidoList()
                .stream()
                .map(item -> produtoGatewayService.findBySku(item.getSkuProduto()))
                .toList();
    }

    private void preenchendoProdutoId(List<ProdutoDTO> produtos, PedidoDTO pedidoDTO) {
        pedidoDTO.getItensPedidoList().forEach(item -> produtos.stream()
                .filter(produto -> produto.getSku().equalsIgnoreCase(item.getSkuProduto()))
                .findFirst()
                .ifPresent(produto -> {
                    item.setProdutoId(produto.getId());
                    item.setQuantidade(estoqueGatewayService
                            .findByIdProduto(produto.getId()).getQuantidade() - item.getQuantidade()
                    );
                    pedidoDTO.setTotalCompra(produto.getPreco().multiply(new BigDecimal(item.getQuantidade())));
                }));
    }

    private BigDecimal calcularTotal(PedidoDTO pedidoDTO, List<ProdutoDTO> produtos) {
        return produtos.stream()
                .map(item -> {
                    BigDecimal precoUnitario = produtoGatewayService.findBySku(item.getSku()).getPreco();
                    return precoUnitario
                            .multiply(BigDecimal.valueOf(
                                    pedidoDTO.getItensPedidoList()
                                            .stream()
                                            .filter(itemPedido ->
                                                    item.getSku().equals(itemPedido.getSkuProduto()))
                                            .findFirst()
                                            .orElseThrow(() -> {
                                                log.error("Produto não encontrado no pedido");
                                                return null;
                                            })
                                            .getQuantidade()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
