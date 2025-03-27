package org.fiap.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.fiap.domain.enums.StatusPagamentoEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PagamentoDTO {

    private Long id;
    private Long pedidoId;
    private String idPagamento;
    private LocalDateTime dtPagamento;
    private StatusPagamentoEnum statusPagamento;
    private LocalDateTime dtAtualizacao;
    private BigDecimal valorTotal;

}
