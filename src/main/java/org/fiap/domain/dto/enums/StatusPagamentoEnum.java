package org.fiap.domain.dto.enums;

import lombok.Getter;

@Getter
public enum StatusPagamentoEnum {

    ABERTO(0, "ABERTO"),
    FECHADO_COM_SUCESSO(1, "FECHADO_COM_SUCESSO"),
    FECHADO_SEM_ESTOQUE(2, "FECHADO_SEM_ESTOQUE"),
    FECHADO_SEM_CREDITO(3, "FECHADO_SEM_CREDITO");

    private final Integer codigo;
    private final String descricao;

    private StatusPagamentoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static StatusPagamentoEnum toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }

        for (StatusPagamentoEnum x : StatusPagamentoEnum.values()) {
            if (cod.equals(x.getCodigo())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Status de Pagamento inválido");
    }
}