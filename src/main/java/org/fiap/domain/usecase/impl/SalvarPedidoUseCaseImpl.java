package org.fiap.domain.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.fiap.domain.dto.PedidoDTO;
import org.fiap.domain.entity.PedidoEntity;
import org.fiap.domain.mapper.PedidoMapper;
import org.fiap.domain.usecase.SalvarPedidoUseCase;
import org.fiap.domain.usecase.ValidarEstoqueUseCase;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SalvarPedidoUseCaseImpl implements SalvarPedidoUseCase {

    private final PedidoMapper pedidoMapper;

    public SalvarPedidoUseCaseImpl(PedidoMapper pedidoMapper) {
        this.pedidoMapper = pedidoMapper;
    }

    @Override
    public PedidoDTO execute(PedidoDTO pedidoDTO) {
        PedidoEntity pedidoEntity = pedidoMapper.convertEntity(pedidoDTO);
        return null;
    }
}
