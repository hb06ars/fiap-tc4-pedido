package org.fiap.infra.config.integration;

import org.fiap.domain.dto.ClienteDTO;
import org.fiap.infra.exceptions.GatewayResponseErrorHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

class ClienteConfigurationTest {

    @InjectMocks
    private ClienteConfiguration clienteConfiguration;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private GatewayResponseErrorHandler gatewayResponseErrorHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testClienteChannel() {
        MessageChannel channel = clienteConfiguration.cliente();
        assertNotNull(channel);
        assertTrue(channel instanceof DirectChannel);
    }

    @Test
    void testFindByIdIntegrationFlow() {
        IntegrationFlow flow = clienteConfiguration.findById();
        assertNotNull(flow);

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(ClienteDTO.class)))
                .thenReturn(null);

    }

    @Test
    void testErrorHandler() {
        GatewayResponseErrorHandler errorHandler = new GatewayResponseErrorHandler();
        assertNotNull(errorHandler);
    }

}
