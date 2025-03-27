package org.fiap.domain.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.fiap.domain.dto.PagamentoDTO;
import org.fiap.domain.entity.ItensPedidoEntity;
import org.fiap.domain.entity.PedidoEntity;
import org.fiap.domain.usecase.CancelarBaixaEstoqueUseCase;
import org.fiap.infra.repository.ItensPedidoRepository;
import org.fiap.infra.repository.PedidoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CancelarBaixaEstoqueUseCaseImpl implements CancelarBaixaEstoqueUseCase {

    private final ItensPedidoRepository itensPedidoRepository;

    public CancelarBaixaEstoqueUseCaseImpl(ItensPedidoRepository itensPedidoRepository) {
        this.itensPedidoRepository = itensPedidoRepository;
    }

    @Override
    public void execute(PagamentoDTO pagamentoDTO) {
        List<ItensPedidoEntity> itensPedido = itensPedidoRepository.findByPedidoId(pagamentoDTO.getPedidoId());
        log.error("Efetuando rollback do Estoque");
    }
}
