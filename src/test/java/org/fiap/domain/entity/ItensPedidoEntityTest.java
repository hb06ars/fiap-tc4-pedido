package org.fiap.domain.entity;

import org.fiap.domain.dto.ItensPedidoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ItensPedidoEntityTest {

    private ItensPedidoDTO dto;

    @BeforeEach
    void setUp() {
        // Criando um DTO de exemplo para a conversão
        dto = ItensPedidoDTO.builder()
                .id(1L)
                .pedidoId(100L)
                .skuProduto("SKU123")
                .quantidade(10)
                .dtAtualizacao(LocalDateTime.now())
                .build();
    }

    @Test
    void testItensPedidoEntityConstructor() {
        // Convertendo o DTO em uma entidade
        ItensPedidoEntity entity = new ItensPedidoEntity(dto);

        assertNotNull(entity);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getPedidoId(), entity.getPedidoId());
        assertEquals(dto.getSkuProduto(), entity.getSkuProduto());
        assertEquals(dto.getQuantidade(), entity.getQuantidade());
        assertEquals(dto.getDtAtualizacao(), entity.getDtAtualizacao());
    }

    @Test
    void testItensPedidoEntityBuilder() {
        // Criando uma entidade com o Builder
        ItensPedidoEntity entity = ItensPedidoEntity.builder()
                .id(1L)
                .pedidoId(100L)
                .skuProduto("SKU123")
                .quantidade(10)
                .dtAtualizacao(LocalDateTime.now())
                .build();

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals(100L, entity.getPedidoId());
        assertEquals("SKU123", entity.getSkuProduto());
        assertEquals(10, entity.getQuantidade());
        assertNotNull(entity.getDtAtualizacao());
    }

    @Test
    void testItensPedidoEntityEmptyConstructor() {
        // Testando o construtor padrão sem parâmetros
        ItensPedidoEntity entity = new ItensPedidoEntity();

        assertNotNull(entity);
        assertNull(entity.getId());
        assertNull(entity.getPedidoId());
        assertNull(entity.getSkuProduto());
        assertNull(entity.getQuantidade());
        assertNull(entity.getDtAtualizacao());
    }
}
