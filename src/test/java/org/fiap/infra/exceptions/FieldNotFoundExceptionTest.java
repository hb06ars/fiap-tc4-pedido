package org.fiap.infra.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FieldNotFoundExceptionTest {

    @Test
    void testFieldNotFoundExceptionWithMessage() {
        String expectedMessage = "Campo não encontrado";
        FieldNotFoundException exception = new FieldNotFoundException(expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testFieldNotFoundExceptionWithMessageAndCause() {
        String expectedMessage = "Campo não encontrado";
        Throwable cause = new Throwable("Causa da exceção");
        FieldNotFoundException exception = new FieldNotFoundException(expectedMessage, cause);

        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testFieldNotFoundExceptionWithoutMessage() {
        // Criando uma exceção sem mensagem
        FieldNotFoundException exception = new FieldNotFoundException(null);

        assertNotNull(exception);
        assertNull(exception.getMessage());
    }

    @Test
    void testFieldNotFoundExceptionWithoutMessageAndCause() {
        FieldNotFoundException exception = new FieldNotFoundException(null, null);

        assertNotNull(exception);
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }
}
