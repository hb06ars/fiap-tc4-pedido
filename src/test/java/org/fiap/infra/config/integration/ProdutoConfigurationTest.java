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

class ProdutoConfigurationTest {

    @InjectMocks
    private ProdutoConfiguration produtoConfiguration;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProdutoChannel() {
        MessageChannel channel = produtoConfiguration.produto();
        assertNotNull(channel);
        assertTrue(channel instanceof DirectChannel);
    }

    @Test
    void testFindByProdutoSkuFlow() {
        IntegrationFlow flow = produtoConfiguration.findByProdutoSku();
        assertNotNull(flow);
    }

    @Test
    void testErrorHandler() {
        GatewayResponseErrorHandler errorHandler = new GatewayResponseErrorHandler();
        assertNotNull(errorHandler);
    }
}
