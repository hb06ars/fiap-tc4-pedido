package org.fiap.domain.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.fiap.app.service.gateway.ProdutoGatewayService;
import org.fiap.domain.dto.PedidoDTO;
import org.fiap.domain.dto.ProdutoDTO;
import org.fiap.domain.usecase.CalcularTotalPedidoUseCase;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Slf4j
public class CalcularTotalPedidoUseCaseImpl implements CalcularTotalPedidoUseCase {

    private final ProdutoGatewayService produtoGatewayService;

    public CalcularTotalPedidoUseCaseImpl(ProdutoGatewayService produtoGatewayService) {
        this.produtoGatewayService = produtoGatewayService;
    }

    @Override
    public BigDecimal execute(PedidoDTO pedidoDTO, List<ProdutoDTO> produtos) {
        log.error("Calculando o total do pedido.");
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
