package org.fiap.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.fiap.domain.dto.PedidoDTO;
import org.fiap.domain.enums.StatusPagamentoEnum;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "pedido")
public class PedidoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    @Positive(message = "O ID do cliente não pode ser menor que 0. Por favor, forneça um valor.")
    private Long clienteId;

    @Column(nullable = false, length = 19)
    @NotBlank(message = "O número do cartão não pode estar em branco. Por favor, forneça um valor.")
    @Size(min = 13, max = 19, message = "O número do cartão deve ter entre 13 e 19 dígitos.")
    @Pattern(regexp = "\\d+", message = "O número do cartão deve conter apenas dígitos.")
    private String numeroCartaoCredito;

    @Column
    private List<ItensPedidoEntity> itensPedidoEntity;

    @Column
    private StatusPagamentoEnum status;

    @Column
    private LocalDateTime dtPedido;

    @Column
    private LocalDateTime dtProcessamento;

    @Column
    private LocalDateTime dtAtualizacao;


    public PedidoEntity(PedidoDTO dto) {
        this.id = dto.getId();
        this.clienteId = dto.getClienteId();
        this.numeroCartaoCredito = dto.getNumeroCartaoCredito();
        this.status = dto.getStatus();
        this.dtPedido = dto.getDtPedido();
        this.itensPedidoEntity = dto.getItensPedidoList()
                .stream()
                .map(item -> ItensPedidoEntity.builder()
                        .id(item.getId())
                        .pedidoId(item.getPedidoId())
                        .dtAtualizacao(item.getDtAtualizacao())
                        .quantidade(item.getQuantidade())
                        .skuProduto(item.getSkuProduto())
                        .build())
                .toList();
        this.dtProcessamento = dto.getDtProcessamento();
        this.dtAtualizacao = dto.getDtAtualizacao();
    }
}
