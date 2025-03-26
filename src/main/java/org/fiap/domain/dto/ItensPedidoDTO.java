package org.fiap.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ItensPedidoDTO {

    private Long id;
    private Long pedidoId;
    private String skuProduto;
    private Long produtoId;
    private Integer quantidade;
    private LocalDateTime dtAtualizacao;

}