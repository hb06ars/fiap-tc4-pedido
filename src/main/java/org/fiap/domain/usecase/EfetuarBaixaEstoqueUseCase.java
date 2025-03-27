package org.fiap.domain.usecase;

import org.fiap.domain.dto.PedidoDTO;

public interface EfetuarBaixaEstoqueUseCase {
    void execute(PedidoDTO pedidoDTO);
}
