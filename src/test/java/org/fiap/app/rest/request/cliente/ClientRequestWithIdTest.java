package org.fiap.app.rest.request.cliente;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ClientRequestWithIdTest {

    @Test
    void testGettersAndSetters() {
        ClienteRequest clienteRequest = ClienteRequest.builder()
                .id(1L)
                .nome("João Silva")
                .cpf("12345678901")
                .build();

        ClientRequestWithId request = new ClientRequestWithId();
        request.setId(10L);
        request.setPayload(clienteRequest);

        assertEquals(10L, request.getId());
        assertNotNull(request.getPayload());
        assertEquals("João Silva", request.getPayload().getNome());
    }

    @Test
    void testAllArgsConstructor() {
        ClienteRequest clienteRequest = ClienteRequest.builder()
                .id(2L)
                .nome("Maria Souza")
                .cpf("98765432100")
                .build();

        ClientRequestWithId request = new ClientRequestWithId(20L, clienteRequest);

        assertEquals(20L, request.getId());
        assertNotNull(request.getPayload());
        assertEquals("Maria Souza", request.getPayload().getNome());
    }

    @Test
    void testNoArgsConstructor() {
        ClientRequestWithId request = new ClientRequestWithId();
        assertNotNull(request);
        assertNull(request.getId());
        assertNull(request.getPayload());
    }

    @Test
    void testBuilder() {
        ClienteRequest clienteRequest = ClienteRequest.builder()
                .id(3L)
                .nome("Carlos Oliveira")
                .cpf("55566677788")
                .build();

        ClientRequestWithId request = ClientRequestWithId.builder()
                .id(30L)
                .payload(clienteRequest)
                .build();

        assertEquals(30L, request.getId());
        assertNotNull(request.getPayload());
        assertEquals("Carlos Oliveira", request.getPayload().getNome());
    }
}
