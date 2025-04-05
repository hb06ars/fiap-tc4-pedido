package org.fiap.app.service.gateway;

import org.fiap.app.gateway.GatewayApi;
import org.fiap.domain.dto.ClienteDTO;
import org.fiap.infra.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.client.ResourceAccessException;

import static org.fiap.domain.util.StringConstants.API_CLIENTES_INDISPONIVEL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteGatewayServiceTest {

    @Mock
    private GatewayApi gatewayApi;

    @InjectMocks
    private ClienteGatewayService clienteGatewayService;

    private ClienteDTO mockCliente;

    @BeforeEach
    void setUp() {
        mockCliente = new ClienteDTO();
        mockCliente.setId(1L);
        mockCliente.setNome("João Silva");
        mockCliente.setCpf("123.456.789-00");
    }

    @Test
    void testFindById_Success() {
        when(gatewayApi.clienteFindById(any(GenericMessage.class))).thenReturn(mockCliente);

        ClienteDTO result = clienteGatewayService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("João Silva", result.getNome());
        assertEquals("123.456.789-00", result.getCpf());
        verify(gatewayApi, times(1)).clienteFindById(any(GenericMessage.class));
    }

    @Test
    void testFindById_ApiIndisponivel() {
        when(gatewayApi.clienteFindById(any(GenericMessage.class))).thenThrow(new ResourceAccessException(API_CLIENTES_INDISPONIVEL));

        Assertions.assertThrows(ObjectNotFoundException.class, () -> {
            clienteGatewayService.findById(2L);
        });

        verify(gatewayApi, times(1)).clienteFindById(any(GenericMessage.class));
    }
}
