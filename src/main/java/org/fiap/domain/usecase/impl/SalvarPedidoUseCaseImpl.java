package org.fiap.domain.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.fiap.domain.dto.PedidoDTO;
import org.fiap.domain.entity.PedidoEntity;
import org.fiap.domain.mapper.PedidoMapper;
import org.fiap.domain.usecase.SalvarPedidoUseCase;
import org.fiap.infra.repository.ItensPedidoRepository;
import org.fiap.infra.repository.PedidoRepository;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SalvarPedidoUseCaseImpl implements SalvarPedidoUseCase {

    private final PedidoRepository pedidoRepository;
    private final ItensPedidoRepository itensPedidoRepository;
    private final PedidoMapper pedidoMapper;

    public SalvarPedidoUseCaseImpl(PedidoRepository pedidoRepository, ItensPedidoRepository itensPedidoRepository, PedidoMapper pedidoMapper) {
        this.pedidoRepository = pedidoRepository;
        this.itensPedidoRepository = itensPedidoRepository;
        this.pedidoMapper = pedidoMapper;
    }

    @Override
    public PedidoDTO execute(PedidoDTO pedidoDTO) {
        PedidoEntity pedidoEntity = pedidoMapper.convertEntity(pedidoDTO);
        var result = pedidoRepository.save(pedidoEntity);
        return new PedidoDTO(result);
    }
}
