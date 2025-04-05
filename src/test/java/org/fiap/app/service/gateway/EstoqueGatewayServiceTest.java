package org.fiap.app.service.gateway;

import org.fiap.app.gateway.GatewayApi;
import org.fiap.app.rest.request.estoque.EstoqueRequest;
import org.fiap.domain.dto.EstoqueDTO;
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

import static org.fiap.domain.util.StringConstants.API_ESTOQUE_INDISPONIVEL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EstoqueGatewayServiceTest {

    @Mock
    private GatewayApi gatewayApi;

    @InjectMocks
    private EstoqueGatewayService estoqueGatewayService;

    private EstoqueDTO mockEstoque;
    private EstoqueRequest mockRequest;

    @BeforeEach
    void setUp() {
        mockEstoque = EstoqueDTO.builder().id(1L).quantidade(100).build();
        mockRequest = EstoqueRequest.builder().id(1L).quantidade(200).build();
    }

    @Test
    void testFindByIdProduto_Success() {
        when(gatewayApi.estoqueFindByIdProduto(any(GenericMessage.class))).thenReturn(mockEstoque);

        EstoqueDTO result = estoqueGatewayService.findByIdProduto(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(100, result.getQuantidade());
        verify(gatewayApi, times(1)).estoqueFindByIdProduto(any(GenericMessage.class));
    }

    @Test
    void testFindByIdProduto_ApiIndisponivel() {
        when(gatewayApi.estoqueFindByIdProduto(any(GenericMessage.class)))
                .thenThrow(new ResourceAccessException(API_ESTOQUE_INDISPONIVEL));

        Assertions.assertThrows(ObjectNotFoundException.class, () -> {
            estoqueGatewayService.findByIdProduto(2L);
        });

        verify(gatewayApi, times(1)).estoqueFindByIdProduto(any());
    }

    @Test
    void testUpdateByIdProduto_Success() {
        when(gatewayApi.estoqueUpdateByIdProduto(any(GenericMessage.class))).thenReturn(mockEstoque);

        EstoqueDTO result = estoqueGatewayService.updateByIdProduto(mockRequest);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(100, result.getQuantidade()); // Simula um update sem mudança real
        verify(gatewayApi, times(1)).estoqueUpdateByIdProduto(any(GenericMessage.class));
    }

    @Test
    void testUpdateByIdProduto_ApiIndisponivel() {

        when(gatewayApi.estoqueUpdateByIdProduto(any(GenericMessage.class))).thenThrow(new ResourceAccessException(API_ESTOQUE_INDISPONIVEL));

        Assertions.assertThrows(ObjectNotFoundException.class, () -> {
            estoqueGatewayService.updateByIdProduto(mockRequest);
        });

        verify(gatewayApi, times(1)).estoqueUpdateByIdProduto(any(GenericMessage.class));
    }
}
