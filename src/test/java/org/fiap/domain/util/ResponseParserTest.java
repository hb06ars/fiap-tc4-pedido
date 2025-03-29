package org.fiap.domain.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.fiap.domain.dto.ProdutoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResponseParserTest {

    @Mock
    private Message<String> message;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ResponseParser responseParser;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        responseParser = new ResponseParser();
    }

    @Test
    void testParseResponse() throws Exception {
        String jsonPayload = """
                {
                    "nome": "Paracetamol 50mg",
                    "sku": "123456784",
                    "preco": 9.95,
                    "dt_criacao": null,
                    "dt_atualizacao": null
                }
                """;
        Message<String> message = new GenericMessage<>(jsonPayload);

        var expectedResponse = ProdutoDTO.builder()
                .nome("Paracetamol 50mg").sku("123456784").preco(BigDecimal.valueOf(9.95)).build();
        ProdutoDTO result = ResponseParser.parseResponse(message, ProdutoDTO.class);

        assertNotNull(result);
        assertEquals(expectedResponse.getNome(), result.getNome());
        assertEquals(expectedResponse.getSku(), result.getSku());
    }

    @Test
    void testParseResponseWithException() throws JsonParseException {
        String invalidJson = "invalid_json";
        Message<String> message = new GenericMessage<>(invalidJson);
        assertThrows(JsonParseException.class, () -> ResponseParser.parseResponse(message, ProdutoDTO.class));
    }
}
