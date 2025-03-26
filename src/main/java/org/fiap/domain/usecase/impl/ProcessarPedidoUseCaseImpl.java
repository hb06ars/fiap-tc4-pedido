package org.fiap.domain.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.fiap.app.service.ClienteGatewayService;
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

    public ProcessarPedidoUseCaseImpl(ClienteGatewayService clienteGatewayService, ProdutoGatewayService produtoGatewayService) {
        this.clienteGatewayService = clienteGatewayService;
        this.produtoGatewayService = produtoGatewayService;
    }

    @Override
    public void execute(PedidoDTO pedidoDTO) {
        ClienteDTO cliente = clienteGatewayService.findById(pedidoDTO.getClienteId());
        List<ProdutoDTO> produtos = buscarProdutos(pedidoDTO);
        BigDecimal totalCompra = calcularTotal(produtos);

        System.out.println(totalCompra);
        System.out.println(produtos);
        System.out.println(cliente);



    }

    private List<ProdutoDTO> buscarProdutos(PedidoDTO pedidoDTO) {
        return pedidoDTO.getItensPedidoList()
                .stream()
                .map(item -> produtoGatewayService.findBySku(item.getSkuProduto()))
                .toList();
    }

    private BigDecimal calcularTotal(List<ProdutoDTO> produtos) {
        return produtos
                .stream()
                .map(item -> produtoGatewayService.findBySku(item.getSku()).getPreco())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
