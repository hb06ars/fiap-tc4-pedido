package org.fiap.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.fiap.domain.dto.enums.StatusPagamentoEnum;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PedidoDTO {

    private Long id;
    private Long clienteId;
    private String numeroCartaoCredito;
    private StatusPagamentoEnum status;
    private List<ItensPedidoDTO> itensPedidoList;
    private LocalDateTime dtPedido;
    private LocalDateTime dtProcessamento;
    private LocalDateTime dtAtualizacao;

}