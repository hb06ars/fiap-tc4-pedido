package org.fiap.domain.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.fiap.app.rest.request.estoque.EstoqueRequest;
import org.fiap.app.service.EstoqueGatewayService;
import org.fiap.app.service.ProdutoGatewayService;
import org.fiap.domain.dto.PagamentoDTO;
import org.fiap.domain.entity.ItensPedidoEntity;
import org.fiap.domain.enums.StatusPagamentoEnum;
import org.fiap.domain.usecase.CancelarBaixaEstoqueUseCase;
import org.fiap.infra.repository.ItensPedidoRepository;
import org.fiap.infra.repository.PedidoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class CancelarBaixaEstoqueUseCaseImpl implements CancelarBaixaEstoqueUseCase {

    private final ItensPedidoRepository itensPedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final EstoqueGatewayService estoqueGatewayService;
    private final ProdutoGatewayService produtoGatewayService;

    public CancelarBaixaEstoqueUseCaseImpl(ItensPedidoRepository itensPedidoRepository, PedidoRepository pedidoRepository, EstoqueGatewayService estoqueGatewayService, ProdutoGatewayService produtoGatewayService) {
        this.itensPedidoRepository = itensPedidoRepository;
        this.pedidoRepository = pedidoRepository;
        this.estoqueGatewayService = estoqueGatewayService;
        this.produtoGatewayService = produtoGatewayService;
    }

    @Override
    public void execute(PagamentoDTO pagamentoDTO) {
        List<ItensPedidoEntity> itensPedido = itensPedidoRepository.findByPedidoId(pagamentoDTO.getPedidoId());
        itensPedido.stream().forEach(item -> {
            var idProduto = produtoGatewayService.findBySku(item.getSkuProduto()).getId();
            var estoqueAtual = estoqueGatewayService.findByIdProduto(idProduto).getQuantidade();
            estoqueGatewayService.updateByIdProduto(
                    EstoqueRequest.builder()
                            .id(idProduto)
                            .quantidade(item.getQuantidade() + estoqueAtual)
                            .build());
        });
        var pedido = pedidoRepository.findById(pagamentoDTO.getPedidoId()).orElse(null);
        if (Objects.nonNull(pedido)) {
            pedido.setStatus(StatusPagamentoEnum.ERRO_NA_API.getDescricao());
            pedidoRepository.save(pedido);
        }
        log.error("Efetuando rollback do Estoque");
    }
}
