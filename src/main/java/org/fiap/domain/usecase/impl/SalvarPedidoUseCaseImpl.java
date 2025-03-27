package org.fiap.domain.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.fiap.domain.dto.PedidoDTO;
import org.fiap.domain.usecase.SalvarPedidoUseCase;
import org.fiap.domain.usecase.ValidarEstoqueUseCase;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SalvarPedidoUseCaseImpl implements SalvarPedidoUseCase {

    @Override
    public PedidoDTO execute(PedidoDTO pedidoDTO) {
        return null;
    }
}
