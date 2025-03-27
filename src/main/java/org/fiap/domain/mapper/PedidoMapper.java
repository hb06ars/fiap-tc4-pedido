package org.fiap.domain.mapper;

import lombok.extern.slf4j.Slf4j;
import org.fiap.app.service.EstoqueGatewayService;
import org.fiap.domain.dto.PedidoDTO;
import org.fiap.domain.dto.ProdutoDTO;
import org.fiap.domain.entity.ItensPedidoEntity;
import org.fiap.domain.entity.PedidoEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Slf4j
public class PedidoMapper {

    private final EstoqueGatewayService estoqueGatewayService;

    public PedidoMapper(EstoqueGatewayService estoqueGatewayService) {
        this.estoqueGatewayService = estoqueGatewayService;
    }

    public void preenchendoProdutoComBaixaNoEstoque(List<ProdutoDTO> produtos, PedidoDTO pedidoDTO) {
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
        log.info(pedidoDTO.toString());
    }

    public PedidoEntity convertEntity(PedidoDTO pedidoDTO) {
        return PedidoEntity.builder()
                .id(pedidoDTO.getId())
                .numeroCartaoCredito(pedidoDTO.getNumeroCartaoCredito())
                .clienteId(pedidoDTO.getClienteId())
                .status(pedidoDTO.getStatus())
                .dtPedido(pedidoDTO.getDtPedido())
                .dtProcessamento(pedidoDTO.getDtProcessamento())
                .dtAtualizacao(pedidoDTO.getDtProcessamento())
                .itensPedidoEntity(pedidoDTO.getItensPedidoList()
                        .stream().map(item -> ItensPedidoEntity.builder()
                                .id(item.getId())
                                .pedidoId(item.getPedidoId())
                                .dtAtualizacao(item.getDtAtualizacao())
                                .quantidade(item.getQuantidade())
                                .skuProduto(item.getSkuProduto())
                                .build()).toList())
                .build();
    }
}
