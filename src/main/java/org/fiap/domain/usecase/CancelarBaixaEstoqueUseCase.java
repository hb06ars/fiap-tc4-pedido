package org.fiap.domain.usecase;

import org.fiap.domain.dto.PagamentoDTO;

public interface CancelarBaixaEstoqueUseCase {
    void execute(PagamentoDTO pagamentoDTO);
}
