package org.fiap.infra.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @Mock
    private WebExchangeBindException webExchangeBindException;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private FieldError fieldError;

    @Test
    void testHandleHttpMessageNotReadableException() {
        HttpMessageNotReadableException exception = new HttpMessageNotReadableException("Invalid JSON format");
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleHttpMessageNotReadableException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().containsKey("erro"));
        assertTrue(response.getBody().containsKey("detalhe"));
        assertTrue(response.getBody().containsKey("statusCode"));
    }

    @Test
    void testHandleInvalidFormatException() {
        InvalidFormatException exception = new InvalidFormatException("Invalid format", null, null, null);
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleInvalidFormatException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().containsKey("erro"));
        assertTrue(response.getBody().containsKey("detalhe"));
        assertTrue(response.getBody().containsKey("statusCode"));
    }

    @Test
    void testHandleValidationExceptions() {
        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
        when(fieldError.getDefaultMessage()).thenReturn("Campo inválido");
        when(fieldError.getField()).thenReturn("campo");

        ResponseEntity<Map<String, Object>> response = globalExceptionHandler.handleValidationExceptions(methodArgumentNotValidException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().containsKey("erro"));
        assertTrue(response.getBody().containsKey("detalhe"));
        assertTrue(response.getBody().containsKey("campo"));
        assertTrue(response.getBody().get("campo").equals("campo"));
    }

    @Test
    void testHandleValidationException() {
        when(webExchangeBindException.getFieldErrors()).thenReturn(List.of(fieldError));
        when(fieldError.getDefaultMessage()).thenReturn("Campo inválido");
        when(fieldError.getField()).thenReturn("campo");

        ResponseEntity<ListErrorResponse> response = globalExceptionHandler.handleValidationException(webExchangeBindException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testHandleObjectNotFoundException() {
        ObjectNotFoundException exception = new ObjectNotFoundException("Objeto não encontrado");
        ResponseEntity<ListErrorResponse> response = globalExceptionHandler.handleObjectNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testHandleFieldNotFoundException() {
        FieldNotFoundException exception = new FieldNotFoundException("Campo não encontrado");
        ResponseEntity<ListErrorResponse> response = globalExceptionHandler.handleFieldNotFoundException(exception);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void testHandleErrorSaveObjectException() {
        Exception exception = new Exception("Erro no sistema");
        ResponseEntity<ListErrorResponse> response = globalExceptionHandler.handleErrorSaveObjectException(exception);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
