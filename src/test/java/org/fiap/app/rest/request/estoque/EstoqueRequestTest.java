package org.fiap.app.rest.request.estoque;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EstoqueRequestTest {

    @Test
    void testGettersAndSetters() {
        EstoqueRequest estoqueRequest = new EstoqueRequest();
        estoqueRequest.setId(1L);
        estoqueRequest.setQuantidade(100);

        assertEquals(1L, estoqueRequest.getId());
        assertEquals(100, estoqueRequest.getQuantidade());
    }

    @Test
    void testAllArgsConstructor() {
        EstoqueRequest estoqueRequest = new EstoqueRequest(2L, 200);

        assertEquals(2L, estoqueRequest.getId());
        assertEquals(200, estoqueRequest.getQuantidade());
    }

    @Test
    void testNoArgsConstructor() {
        EstoqueRequest estoqueRequest = new EstoqueRequest();

        assertNotNull(estoqueRequest);
        assertNull(estoqueRequest.getId());
        assertNull(estoqueRequest.getQuantidade());
    }

    @Test
    void testBuilder() {
        EstoqueRequest estoqueRequest = EstoqueRequest.builder()
                .id(3L)
                .quantidade(300)
                .build();

        assertEquals(3L, estoqueRequest.getId());
        assertEquals(300, estoqueRequest.getQuantidade());
    }
}
