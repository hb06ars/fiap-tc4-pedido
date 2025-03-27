package org.fiap.domain.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.fiap.domain.dto.PedidoDTO;
import org.fiap.domain.usecase.ValidarEstoqueUseCase;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ValidarEstoqueUseCaseImpl implements ValidarEstoqueUseCase {

    @Override
    public boolean execute(PedidoDTO pedidoDTO) {
        var estoqueInvalido = pedidoDTO.getItensPedidoList()
                .stream()
                .anyMatch(item -> item.getQuantidade() < 0);

        if (estoqueInvalido) {
            log.error("Estoque inválido.");
            return false;
        }
        log.info("Estoque válido.");
        return true;
    }
}
