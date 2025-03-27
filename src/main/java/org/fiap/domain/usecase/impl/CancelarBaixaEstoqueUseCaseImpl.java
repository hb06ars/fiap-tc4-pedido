package org.fiap.domain.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.fiap.domain.dto.PagamentoDTO;
import org.fiap.domain.usecase.CancelarBaixaEstoqueUseCase;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@Transactional
public class CancelarBaixaEstoqueUseCaseImpl implements CancelarBaixaEstoqueUseCase {

    @Override
    public void execute(PagamentoDTO pagamentoDTO) {
        log.info("Efetuando rollback do Estoque");
    }
}
