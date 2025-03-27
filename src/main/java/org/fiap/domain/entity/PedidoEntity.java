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
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.fiap.domain.dto.PedidoDTO;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pedido")
public class PedidoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cliente_id", nullable = false, length = 255)
    @Positive(message = "O ID do cliente não pode ser menor que 0. Por favor, forneça um valor.")
    private Long clienteId;

    @Column(name = "numero_cartao_credito", nullable = false, length = 19)
    @NotBlank(message = "O número do cartão não pode estar em branco. Por favor, forneça um valor.")
    @Size(min = 13, max = 19, message = "O número do cartão deve ter entre 13 e 19 dígitos.")
    @Pattern(regexp = "\\d+", message = "O número do cartão deve conter apenas dígitos.")
    private String numeroCartaoCredito;

    @Column
    private String status;

    @Column(name = "dt_pedido")
    private LocalDateTime dtPedido;

    @Column(name = "dt_processamento")
    private LocalDateTime dtProcessamento;

    @Column(name = "dt_atualizacao")
    private LocalDateTime dtAtualizacao;


    public PedidoEntity(PedidoDTO dto) {
        this.id = dto.getId();
        this.clienteId = dto.getClienteId();
        this.numeroCartaoCredito = dto.getNumeroCartaoCredito();
        this.status = dto.getStatus().getDescricao();
        this.dtPedido = dto.getDtPedido();
        this.dtProcessamento = dto.getDtProcessamento();
        this.dtAtualizacao = dto.getDtAtualizacao();
    }
}
