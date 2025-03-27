package org.fiap.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.fiap.domain.entity.ItensPedidoEntity;

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
    private LocalDateTime dtAtualizacao = LocalDateTime.now();

    public ItensPedidoDTO(ItensPedidoEntity entity) {
        this.id = entity.getId();
        this.pedidoId = entity.getPedidoId();
        this.skuProduto = entity.getSkuProduto();
        this.quantidade = entity.getQuantidade();
        this.dtAtualizacao = entity.getDtAtualizacao();
    }

    public ItensPedidoDTO(ItensPedidoDTO dto) {
        this.id = dto.getId();
        this.pedidoId = dto.getPedidoId();
        this.skuProduto = dto.getSkuProduto();
        this.quantidade = dto.getQuantidade();
        this.dtAtualizacao = dto.getDtAtualizacao();
    }
}