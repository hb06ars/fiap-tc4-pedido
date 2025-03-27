package org.fiap.domain.usecase;

import org.fiap.domain.dto.PedidoDTO;

public interface SalvarPedidoUseCase {
    PedidoDTO execute(PedidoDTO pedidoDTO);
}
