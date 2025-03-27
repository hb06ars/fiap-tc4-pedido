package org.fiap.domain.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.fiap.app.rest.request.estoque.EstoqueRequest;
import org.fiap.app.service.EstoqueGatewayService;
import org.fiap.domain.dto.PedidoDTO;
import org.fiap.domain.usecase.EfetuarBaixaEstoqueUseCase;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@Transactional
public class EfetuarBaixaEstoqueUseCaseImpl implements EfetuarBaixaEstoqueUseCase {

    private final EstoqueGatewayService estoqueGatewayService;

    public EfetuarBaixaEstoqueUseCaseImpl(EstoqueGatewayService estoqueGatewayService) {
        this.estoqueGatewayService = estoqueGatewayService;
    }

    @Override
    public void execute(PedidoDTO pedidoDTO) {
        log.info("Baixa de Estoque em execução.");
        pedidoDTO.getItensPedidoList().forEach(item ->
                estoqueGatewayService.updateByIdProduto(
                        EstoqueRequest.builder()
                                .id(item.getProdutoId())
                                .quantidade(item.getQuantidade())
                                .build()
                ));
        log.info("Baixa de Estoque finalizada.");
    }
}
