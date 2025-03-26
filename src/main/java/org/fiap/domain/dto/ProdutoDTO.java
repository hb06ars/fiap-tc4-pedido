package org.fiap.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ProdutoDTO {

    private Long id;
    private String nome;
    private String sku;
    private BigDecimal preco;
    private LocalDateTime dt_criacao = LocalDateTime.now();
    private LocalDateTime dt_atualizacao = LocalDateTime.now();

}
