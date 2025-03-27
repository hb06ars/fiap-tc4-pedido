package org.fiap.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.fiap.domain.entity.PedidoEntity;
import org.fiap.domain.enums.StatusPagamentoEnum;

import java.math.BigDecimal;
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
    private BigDecimal totalCompra;
    private LocalDateTime dtPedido;
    private LocalDateTime dtProcessamento;
    private LocalDateTime dtAtualizacao;


    public PedidoDTO(PedidoEntity entity) {
        this.id = entity.getId();
        this.clienteId = entity.getClienteId();
        this.numeroCartaoCredito = entity.getNumeroCartaoCredito();
        this.status = entity.getStatus();
        this.dtPedido = entity.getDtPedido();
        this.dtProcessamento = entity.getDtProcessamento();
        this.dtAtualizacao = entity.getDtAtualizacao();
        this.itensPedidoList = entity.getItensPedidoEntity()
                .stream()
                .map(item -> ItensPedidoDTO.builder()
                        .id(item.getId())
                        .pedidoId(item.getPedidoId())
                        .dtAtualizacao(item.getDtAtualizacao())
                        .quantidade(item.getQuantidade())
                        .skuProduto(item.getSkuProduto())
                        .build())
                .toList();
    }
}