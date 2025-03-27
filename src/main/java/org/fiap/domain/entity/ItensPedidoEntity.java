package org.fiap.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.fiap.domain.dto.ItensPedidoDTO;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pedido")
public class ItensPedidoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    @Positive(message = "O ID do Pedido não pode ser menor que 0. Por favor, forneça um valor.")
    private Long pedidoId;

    @Column(nullable = false, length = 255)
    @NotEmpty(message = "O SKU do Produto não pode servazio. Por favor, forneça um valor.")
    private String skuProduto;

    @Column(nullable = false)
    @Positive(message = "A quantidade não pode ser menor que 0. Por favor, forneça um valor.")
    private Integer quantidade;

    @Column
    private LocalDateTime dtAtualizacao;

    public ItensPedidoEntity(ItensPedidoDTO dto) {
        this.id = dto.getId();
        this.pedidoId = dto.getPedidoId();
        this.skuProduto = dto.getSkuProduto();
        this.quantidade = dto.getQuantidade();
        this.dtAtualizacao = dto.getDtAtualizacao();
    }
}
