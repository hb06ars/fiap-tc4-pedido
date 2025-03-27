package org.fiap.domain.enums;

import lombok.Getter;

@Getter
public enum StatusPagamentoEnum {

    ABERTO("ABERTO"),
    FECHADO_COM_SUCESSO("FECHADO_COM_SUCESSO"),
    FECHADO_SEM_ESTOQUE("FECHADO_SEM_ESTOQUE"),
    FECHADO_SEM_CREDITO("FECHADO_SEM_CREDITO"),
    ERRO_NA_API("ERRO_NA_API");

    private final String descricao;

    StatusPagamentoEnum(String descricao) {
        this.descricao = descricao;
    }
}