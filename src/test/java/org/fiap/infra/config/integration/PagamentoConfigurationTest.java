package org.fiap.infra.config.integration;

import org.fiap.infra.exceptions.GatewayResponseErrorHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.MessageChannel;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PagamentoConfigurationTest {

    @InjectMocks
    private PagamentoConfiguration pagamentoConfiguration;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPagamentoChannel() {
        MessageChannel channel = pagamentoConfiguration.pagamento();
        assertNotNull(channel);
        assertTrue(channel instanceof DirectChannel);
    }

    @Test
    void testSalvandoPagamentoFlow() {
        IntegrationFlow flow = pagamentoConfiguration.salvandoPagamento();
        assertNotNull(flow);
    }

    @Test
    void testErrorHandler() {
        GatewayResponseErrorHandler errorHandler = new GatewayResponseErrorHandler();
        assertNotNull(errorHandler);
    }
}
