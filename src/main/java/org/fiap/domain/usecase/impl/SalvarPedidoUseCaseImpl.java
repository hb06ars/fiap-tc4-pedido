package org.fiap.domain.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.fiap.app.service.postgres.ItensPedidoService;
import org.fiap.app.service.postgres.PedidoService;
import org.fiap.domain.dto.PedidoDTO;
import org.fiap.domain.entity.ItensPedidoEntity;
import org.fiap.domain.entity.PedidoEntity;
import org.fiap.domain.mapper.PedidoMapper;
import org.fiap.domain.usecase.SalvarPedidoUseCase;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SalvarPedidoUseCaseImpl implements SalvarPedidoUseCase {

    private final PedidoService pedidoService;
    private final ItensPedidoService itensPedidoService;
    private final PedidoMapper pedidoMapper;

    public SalvarPedidoUseCaseImpl(PedidoService pedidoService, ItensPedidoService itensPedidoService, PedidoMapper pedidoMapper) {
        this.pedidoService = pedidoService;
        this.itensPedidoService = itensPedidoService;
        this.pedidoMapper = pedidoMapper;
    }


    @Override
    public PedidoDTO execute(PedidoDTO pedidoDTO) {
        PedidoEntity pedidoEntity = pedidoMapper.convertEntity(pedidoDTO);
        var result = pedidoService.save(pedidoEntity);
        if (result.getId() != null) {
            pedidoDTO.setId(result.getId());
            pedidoDTO.setValorTotal(result.getValorTotal());
            pedidoDTO.getItensPedidoList().forEach(item -> {
                var itemEntity = itensPedidoService.save(ItensPedidoEntity.builder()
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
