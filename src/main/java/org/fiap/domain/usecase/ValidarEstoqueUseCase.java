package org.fiap.domain.usecase;

import org.fiap.domain.dto.PedidoDTO;

public interface ValidarEstoqueUseCase {
    boolean execute(PedidoDTO pedidoDTO);
}
