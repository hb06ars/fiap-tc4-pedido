package org.fiap.domain.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.fiap.domain.dto.PedidoDTO;
import org.fiap.domain.entity.ItensPedidoEntity;
import org.fiap.domain.entity.PedidoEntity;
import org.fiap.domain.mapper.PedidoMapper;
import org.fiap.domain.usecase.SalvarPedidoUseCase;
import org.fiap.infra.repository.ItensPedidoRepository;
import org.fiap.infra.repository.PedidoRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@Transactional
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
        if (result.getId() != null) {
            pedidoDTO.setId(result.getId());
            pedidoDTO.getItensPedidoList().forEach(item -> {
                var itemEntity = itensPedidoRepository.save(ItensPedidoEntity.builder()
                        .pedidoId(result.getId())
                        .skuProduto(item.getSkuProduto())
                        .quantidade(item.getQuantidade())
                        .dtAtualizacao(item.getDtAtualizacao())
                        .build());

                item.setId(itemEntity.getId());
                item.setPedidoId(itemEntity.getPedidoId());
                item.setSkuProduto(itemEntity.getSkuProduto());
                item.setQuantidade(itemEntity.getQuantidade());
                item.setDtAtualizacao(itemEntity.getDtAtualizacao());
            });

        }
        return pedidoDTO;
    }
}
