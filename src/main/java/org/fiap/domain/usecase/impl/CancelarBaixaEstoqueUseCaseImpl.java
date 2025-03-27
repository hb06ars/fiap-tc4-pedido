package org.fiap.domain.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.fiap.app.rest.request.estoque.EstoqueRequest;
import org.fiap.app.service.gateway.EstoqueGatewayService;
import org.fiap.app.service.gateway.ProdutoGatewayService;
import org.fiap.app.service.postgres.ItensPedidoService;
import org.fiap.app.service.postgres.PedidoService;
import org.fiap.domain.dto.ItensPedidoDTO;
import org.fiap.domain.dto.PagamentoDTO;
import org.fiap.domain.entity.PedidoEntity;
import org.fiap.domain.enums.StatusPagamentoEnum;
import org.fiap.domain.usecase.CancelarBaixaEstoqueUseCase;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class CancelarBaixaEstoqueUseCaseImpl implements CancelarBaixaEstoqueUseCase {

    private final ItensPedidoService itensPedidoService;
    private final PedidoService pedidoService;
    private final EstoqueGatewayService estoqueGatewayService;
    private final ProdutoGatewayService produtoGatewayService;

    public CancelarBaixaEstoqueUseCaseImpl(ItensPedidoService itensPedidoService, PedidoService pedidoService, EstoqueGatewayService estoqueGatewayService, ProdutoGatewayService produtoGatewayService) {
        this.itensPedidoService = itensPedidoService;
        this.pedidoService = pedidoService;
        this.estoqueGatewayService = estoqueGatewayService;
        this.produtoGatewayService = produtoGatewayService;
    }


    @Override
    public PagamentoDTO execute(PagamentoDTO pagamentoDTO) {
        List<ItensPedidoDTO> itensPedido = itensPedidoService
                .findByPedidoId(pagamentoDTO.getPedidoId())
                .stream().map(ItensPedidoDTO::new)
                .toList();

        itensPedido.forEach(item -> {
            var idProduto = produtoGatewayService.findBySku(item.getSkuProduto()).getId();
            var estoqueAtual = estoqueGatewayService.findByIdProduto(idProduto).getQuantidade();
            estoqueGatewayService.updateByIdProduto(
                    EstoqueRequest.builder()
                            .id(idProduto)
                            .quantidade(estoqueAtual + item.getQuantidade())
                            .build());
        });
        var pedido = pedidoService.findById(pagamentoDTO.getPedidoId());
        if (Objects.nonNull(pedido)) {
            if(pagamentoDTO.getStatusPagamento() == null)
                pedido.setStatus(StatusPagamentoEnum.ERRO_NA_API);
            else
                pedido.setStatus(pagamentoDTO.getStatusPagamento());
            pedidoService.save(new PedidoEntity(pedido));
        }
        log.error("Rollback do Estoque efetuado");
        return pagamentoDTO;
    }
}
