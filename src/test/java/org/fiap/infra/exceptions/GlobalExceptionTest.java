package org.fiap.infra.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class GlobalExceptionTest {

    @Test
    void testGlobalExceptionWithMessage() {
        String errorMessage = "Erro desconhecido";
        GlobalException exception = new GlobalException(errorMessage);
        assertEquals(errorMessage, exception.getMessage(), "A mensagem da exceção deve ser a mesma que a fornecida");
    }

    @Test
    void testGlobalExceptionWithMessageAndCause() {
        String errorMessage = "Erro causado por outra exceção";
        Throwable cause = new Throwable("Causa da exceção");

        GlobalException exception = new GlobalException(errorMessage, cause);
        assertEquals(errorMessage, exception.getMessage(), "A mensagem da exceção deve ser a mesma que a fornecida");
        assertEquals(cause, exception.getCause(), "A causa da exceção deve ser a mesma que a fornecida");
    }

    @Test
    void testGlobalExceptionWithoutMessage() {
        GlobalException exception = new GlobalException(null);
        assertNull(exception.getMessage(), "A exceção não deve ter mensagem, pois foi fornecida como null");
    }
}
