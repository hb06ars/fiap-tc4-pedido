package org.fiap.domain.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.fiap.domain.dto.PagamentoDTO;
import org.fiap.domain.usecase.CancelarBaixaEstoqueUseCase;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CancelarBaixaEstoqueUseCaseImpl implements CancelarBaixaEstoqueUseCase {

    @Override
    public void execute(PagamentoDTO pagamentoDTO) {
        log.error("Efetuando rollback do Estoque");
    }
}
