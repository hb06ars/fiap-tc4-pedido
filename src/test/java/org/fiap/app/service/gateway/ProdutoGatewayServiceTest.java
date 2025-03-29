package org.fiap.app.service.gateway;

import org.fiap.app.gateway.GatewayApi;
import org.fiap.domain.dto.ProdutoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.client.ResourceAccessException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProdutoGatewayServiceTest {

    @Mock
    private GatewayApi gatewayApi;

    @InjectMocks
    private ProdutoGatewayService produtoGatewayService;

    private ProdutoDTO mockProduto;

    @BeforeEach
    void setUp() {
        mockProduto = ProdutoDTO.builder().id(1L).sku("ABC123").nome("Produto Teste").build();
    }

    @Test
    void testFindBySku_Success() {
        when(gatewayApi.produtoFindBySku(any(GenericMessage.class))).thenReturn(mockProduto);

        ProdutoDTO result = produtoGatewayService.findBySku("ABC123");

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("ABC123", result.getSku());
        assertEquals("Produto Teste", result.getNome());
        verify(gatewayApi, times(1)).produtoFindBySku(any(GenericMessage.class));
    }

    @Test
    void testFindBySku_ApiIndisponivel() {
        when(gatewayApi.produtoFindBySku(any(GenericMessage.class)))
                .thenThrow(new ResourceAccessException("API Produto Indisponível"));

        ProdutoDTO result = produtoGatewayService.findBySku("ABC123");

        assertNull(result);
        verify(gatewayApi, times(1)).produtoFindBySku(any(GenericMessage.class));
    }
}
