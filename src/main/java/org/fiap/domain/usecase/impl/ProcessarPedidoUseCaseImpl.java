package org.fiap.domain.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.fiap.app.service.ClienteGatewayService;
import org.fiap.app.service.EstoqueGatewayService;
import org.fiap.app.service.ProdutoGatewayService;
import org.fiap.domain.dto.ClienteDTO;
import org.fiap.domain.dto.PedidoDTO;
import org.fiap.domain.dto.ProdutoDTO;
import org.fiap.domain.usecase.ProcessarPedidoUseCase;
import org.fiap.infra.exceptions.GlobalException;
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
        List<ProdutoDTO> produtos = buscarProdutos(pedidoDTO);
        BigDecimal totalCompra = calcularTotal(produtos);
        pedidoDTO.setTotalCompra(totalCompra);

        ClienteDTO cliente = clienteGatewayService.findById(pedidoDTO.getClienteId());
        
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
