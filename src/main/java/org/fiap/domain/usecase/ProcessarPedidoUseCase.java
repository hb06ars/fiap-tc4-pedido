package org.fiap.domain.usecase;

import org.fiap.domain.dto.PedidoDTO;

public interface ProcessarPedidoUseCase {
    void execute(PedidoDTO pedidoDTO);
}
