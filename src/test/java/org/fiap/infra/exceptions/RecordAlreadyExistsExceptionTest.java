package org.fiap.infra.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RecordAlreadyExistsExceptionTest {

    @Test
    void testConstructorWithMessage() {
        String message = "Record already exists";
        RecordAlreadyExistsException exception = new RecordAlreadyExistsException(message);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testConstructorWithMessageAndCause() {
        String message = "Record already exists";
        Throwable cause = new Throwable("Cause of the exception");
        RecordAlreadyExistsException exception = new RecordAlreadyExistsException(message, cause);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

}
