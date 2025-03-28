package org.fiap.infra.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ObjectNotFoundExceptionTest {

    @Test
    void testExceptionWithMessage() {
        String expectedMessage = "Object not found";
        ObjectNotFoundException exception = new ObjectNotFoundException(expectedMessage);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testExceptionWithMessageAndCause() {
        String expectedMessage = "Object not found";
        Throwable expectedCause = new Throwable("Cause of the error");
        ObjectNotFoundException exception = new ObjectNotFoundException(expectedMessage, expectedCause);
        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(expectedCause, exception.getCause());
    }

    @Test
    void testExceptionWithCause() {
        Throwable expectedCause = new Throwable("Cause of the error");
        ObjectNotFoundException exception = new ObjectNotFoundException("Object not found", expectedCause);
        assertEquals(expectedCause, exception.getCause());
    }

    @Test
    void testToStringMethod() {
        String expectedMessage = "Object not found";
        Throwable expectedCause = new Throwable("Cause of the error");
        ObjectNotFoundException exception = new ObjectNotFoundException(expectedMessage, expectedCause);
        assertTrue(exception.getMessage().toString().contains(expectedMessage));
    }
}
