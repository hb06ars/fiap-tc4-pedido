package org.fiap.infra.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class GatewayResponseErrorHandlerTest {

    private GatewayResponseErrorHandler errorHandler;

    @Mock
    private ClientHttpResponse clientHttpResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        errorHandler = new GatewayResponseErrorHandler();
    }

    @Test
    void testHasError_WhenErrorStatusCode_ReturnsTrue() throws IOException {
        when(clientHttpResponse.getStatusCode()).thenReturn(HttpStatus.BAD_REQUEST);
        boolean result = errorHandler.hasError(clientHttpResponse);
        assertTrue(result, "Deve retornar true quando o status é de erro");
    }

    @Test
    void testHasError_WhenSuccessStatusCode_ReturnsFalse() throws IOException {
        when(clientHttpResponse.getStatusCode()).thenReturn(HttpStatus.OK);
        boolean result = errorHandler.hasError(clientHttpResponse);
        assertFalse(result, "Deve retornar false quando o status é de sucesso");
    }

    @Test
    void testHandleError_WhenNotFoundStatusCode_ThrowsObjectNotFoundException() throws IOException {
        when(clientHttpResponse.getStatusCode()).thenReturn(HttpStatus.NOT_FOUND);
        assertThrows(ObjectNotFoundException.class, () -> errorHandler.handleError(clientHttpResponse));
    }

    @Test
    void testHandleError_WhenBadRequestStatusCode_ThrowsGlobalException() throws IOException {
        when(clientHttpResponse.getStatusCode()).thenReturn(HttpStatus.BAD_REQUEST);
        assertThrows(GlobalException.class, () -> errorHandler.handleError(clientHttpResponse));
    }

    @Test
    void testHandleError_WhenOtherStatusCode_ThrowsGlobalException() throws IOException {
        when(clientHttpResponse.getStatusCode()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThrows(GlobalException.class, () -> errorHandler.handleError(clientHttpResponse));
    }
}
