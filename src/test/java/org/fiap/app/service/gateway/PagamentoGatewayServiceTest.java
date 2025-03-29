package org.fiap.app.service.gateway;

import org.fiap.app.gateway.GatewayApi;
import org.fiap.domain.dto.PagamentoDTO;
import org.fiap.domain.enums.StatusPagamentoEnum;
import org.fiap.domain.util.StringConstants;
import org.fiap.infra.exceptions.GlobalException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PagamentoGatewayServiceTest {

    @Mock
    private GatewayApi gatewayApi;

    @InjectMocks
    private PagamentoGatewayService pagamentoGatewayService;

    private PagamentoDTO mockPagamento;

    @BeforeEach
    void setUp() {
        mockPagamento = PagamentoDTO.builder().id(1L).valorTotal(BigDecimal.valueOf(100.0)).statusPagamento(StatusPagamentoEnum.FECHADO_COM_SUCESSO).build();
    }

    @Test
    void testSave_Success() {
        when(gatewayApi.pagamentoSave(any(GenericMessage.class))).thenReturn(mockPagamento);

        PagamentoDTO result = pagamentoGatewayService.save(mockPagamento);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(BigDecimal.valueOf(100.0), result.getValorTotal());
        assertEquals(StatusPagamentoEnum.FECHADO_COM_SUCESSO, result.getStatusPagamento());
        verify(gatewayApi, times(1)).pagamentoSave(any(GenericMessage.class));
    }

    @Test
    void testSave_ApiIndisponivel() {
        when(gatewayApi.pagamentoSave(any(GenericMessage.class)))
                .thenThrow(new ResourceAccessException("API Pagamento Indisponível"));

        GlobalException exception = assertThrows(GlobalException.class, () -> pagamentoGatewayService.save(mockPagamento));

        assertEquals(StringConstants.API_PAGAMENTO_INDISPONIVEL, exception.getMessage());
        verify(gatewayApi, times(1)).pagamentoSave(any(GenericMessage.class));
    }
}
