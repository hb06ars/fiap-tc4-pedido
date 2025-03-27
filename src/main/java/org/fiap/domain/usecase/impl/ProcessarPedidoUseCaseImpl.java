package org.fiap.domain.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.fiap.app.service.ClienteGatewayService;
import org.fiap.app.service.EstoqueGatewayService;
import org.fiap.app.service.ProdutoGatewayService;
import org.fiap.domain.dto.ClienteDTO;
import org.fiap.domain.dto.PedidoDTO;
import org.fiap.domain.dto.ProdutoDTO;
import org.fiap.domain.usecase.CalcularTotalPedidoUseCase;
import org.fiap.domain.usecase.EfetuarBaixaEstoqueUseCase;
import org.fiap.domain.usecase.ProcessarPedidoUseCase;
import org.fiap.domain.usecase.ValidarEstoqueUseCase;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Slf4j
public class ProcessarPedidoUseCaseImpl implements ProcessarPedidoUseCase {

    private final ClienteGatewayService clienteGatewayService;
    private final ProdutoGatewayService produtoGatewayService;
    private final EstoqueGatewayService estoqueGatewayService;
    private final ValidarEstoqueUseCase validarEstoqueUseCase;
    private final EfetuarBaixaEstoqueUseCase efetuarBaixaEstoqueUseCase;
    private final CalcularTotalPedidoUseCase calcularTotalPedidoUseCase;

    public ProcessarPedidoUseCaseImpl(ClienteGatewayService clienteGatewayService, ProdutoGatewayService produtoGatewayService, EstoqueGatewayService estoqueGatewayService, ValidarEstoqueUseCase validarEstoqueUseCase, EfetuarBaixaEstoqueUseCase efetuarBaixaEstoqueUseCase, CalcularTotalPedidoUseCase calcularTotalPedidoUseCase) {
        this.clienteGatewayService = clienteGatewayService;
        this.produtoGatewayService = produtoGatewayService;
        this.estoqueGatewayService = estoqueGatewayService;
        this.validarEstoqueUseCase = validarEstoqueUseCase;
        this.efetuarBaixaEstoqueUseCase = efetuarBaixaEstoqueUseCase;
        this.calcularTotalPedidoUseCase = calcularTotalPedidoUseCase;
    }

    @Override
    public void execute(PedidoDTO pedidoDTO) {
        ClienteDTO cliente = clienteGatewayService.findById(pedidoDTO.getClienteId());
        List<ProdutoDTO> produtos = buscarProdutos(pedidoDTO);
        BigDecimal totalCompra = calcularTotalPedidoUseCase.execute(pedidoDTO, produtos);
        pedidoDTO.setTotalCompra(totalCompra);
        preenchendoProdutoId(produtos, pedidoDTO);

        if (baixaEstoqueEfetuada(pedidoDTO)) {
            log.info("SALVAR PEDIDO E ITENS DO PEDIDO.");
        }

    }

    private boolean baixaEstoqueEfetuada(PedidoDTO pedidoDTO) {
        if (validarEstoqueUseCase.execute(pedidoDTO)) {
            efetuarBaixaEstoqueUseCase.execute(pedidoDTO);
            return true;
        }
        return false;
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

}
