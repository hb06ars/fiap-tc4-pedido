package org.fiap.domain.entity;

import org.fiap.domain.dto.PedidoDTO;
import org.fiap.domain.enums.StatusPagamentoEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class PedidoEntityTest {

    private PedidoDTO dto;

    @BeforeEach
    void setUp() {
        // Criando um DTO de exemplo para a conversão
        dto = PedidoDTO.builder()
                .id(1L)
                .clienteId(100L)
                .numeroCartaoCredito("1234567890123456")
                .status(StatusPagamentoEnum.ABERTO)
                .dtPedido(LocalDateTime.now())
                .dtProcessamento(LocalDateTime.now())
                .dtAtualizacao(LocalDateTime.now())
                .valorTotal(BigDecimal.valueOf(150.75))
                .build();
    }

    @Test
    void testPedidoEntityConstructor() {
        // Convertendo o DTO em uma entidade
        PedidoEntity entity = new PedidoEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getClienteId(), entity.getClienteId());
        assertEquals(dto.getNumeroCartaoCredito(), entity.getNumeroCartaoCredito());
        assertEquals(dto.getStatus().getDescricao(), entity.getStatus());
        assertEquals(dto.getDtPedido(), entity.getDtPedido());
        assertEquals(dto.getDtProcessamento(), entity.getDtProcessamento());
        assertEquals(dto.getDtAtualizacao(), entity.getDtAtualizacao());
        assertEquals(dto.getValorTotal(), entity.getValorTotal());
    }

    @Test
    void testPedidoEntityBuilder() {
        // Criando uma entidade com o Builder
        PedidoEntity entity = PedidoEntity.builder()
                .id(1L)
                .clienteId(100L)
                .numeroCartaoCredito("1234567890123456")
                .status("PENDENTE")
                .dtPedido(LocalDateTime.now())
                .dtProcessamento(LocalDateTime.now())
                .dtAtualizacao(LocalDateTime.now())
                .valorTotal(BigDecimal.valueOf(150.75))
                .build();

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals(100L, entity.getClienteId());
        assertEquals("1234567890123456", entity.getNumeroCartaoCredito());
        assertEquals("PENDENTE", entity.getStatus());
        assertNotNull(entity.getDtPedido());
        assertNotNull(entity.getDtProcessamento());
        assertNotNull(entity.getDtAtualizacao());
        assertEquals(BigDecimal.valueOf(150.75), entity.getValorTotal());
    }

    @Test
    void testPedidoEntityEmptyConstructor() {
        // Testando o construtor padrão sem parâmetros
        PedidoEntity entity = new PedidoEntity();

        assertNotNull(entity);
        assertNull(entity.getId());
        assertNull(entity.getClienteId());
        assertNull(entity.getNumeroCartaoCredito());
        assertNull(entity.getStatus());
        assertNull(entity.getDtPedido());
        assertNull(entity.getDtProcessamento());
        assertNull(entity.getDtAtualizacao());
        assertNull(entity.getValorTotal());
    }
}
