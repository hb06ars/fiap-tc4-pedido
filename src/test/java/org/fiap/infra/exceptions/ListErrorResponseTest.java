package org.fiap.infra.exceptions;

import org.fiap.domain.dto.MessageErrorDTO;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ListErrorResponseTest {

    @Test
    void testConstructorAndGetters() {
        MessageErrorDTO messageErrorDTO = MessageErrorDTO.builder()
                .erro("Erro de validação")
                .detalhe("Campo obrigatório não preenchido")
                .build();
        ListErrorResponse response = new ListErrorResponse(
                Collections.singletonList(messageErrorDTO),
                400
        );

        assertNotNull(response);
        assertEquals(1, response.getMessage().size());
        assertEquals("Erro de validação", response.getMessage().get(0).getErro());
        assertEquals("Campo obrigatório não preenchido", response.getMessage().get(0).getDetalhe());
        assertEquals(400, response.getStatusCode());
    }

    @Test
    void testSettersAndGetters() {
        MessageErrorDTO messageErrorDTO = MessageErrorDTO.builder()
                .erro("Erro no sistema")
                .detalhe("Erro desconhecido")
                .build();

        ListErrorResponse response = new ListErrorResponse(Collections.emptyList(), 0);

        response.setMessage(Collections.singletonList(messageErrorDTO));
        response.setStatusCode(500);

        assertNotNull(response);
        assertEquals(1, response.getMessage().size());
        assertEquals("Erro no sistema", response.getMessage().get(0).getErro());
        assertEquals("Erro desconhecido", response.getMessage().get(0).getDetalhe());
        assertEquals(500, response.getStatusCode());
    }

    @Test
    void testBuilder() {
        MessageErrorDTO messageErrorDTO = MessageErrorDTO.builder()
                .erro("Campo inválido")
                .detalhe("O campo nome não pode estar vazio")
                .build();

        ListErrorResponse response = ListErrorResponse.builder()
                .message(Collections.singletonList(messageErrorDTO))
                .statusCode(422)
                .build();

        assertNotNull(response);
        assertEquals(1, response.getMessage().size());
        assertEquals("Campo inválido", response.getMessage().get(0).getErro());
        assertEquals("O campo nome não pode estar vazio", response.getMessage().get(0).getDetalhe());
        assertEquals(422, response.getStatusCode());
    }
}
