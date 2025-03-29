package org.fiap.app.rest.controller;

import org.fiap.app.service.postgres.PedidoService;
import org.fiap.domain.dto.PagamentoDTO;
import org.fiap.domain.dto.PedidoDTO;
import org.fiap.domain.usecase.CancelarBaixaEstoqueUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ControllerTest {

    @Mock
    private PedidoService pedidoService;

    @Mock
    private CancelarBaixaEstoqueUseCase cancelarBaixaEstoqueUseCase;

    @InjectMocks
    private Controller controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBuscarPedido() {
        Long pedidoId = 1L;
        PedidoDTO pedidoDTO = new PedidoDTO();
        when(pedidoService.findById(pedidoId)).thenReturn(pedidoDTO);

        ResponseEntity<PedidoDTO> response = controller.buscarPedido(pedidoId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(pedidoDTO, response.getBody());
        verify(pedidoService, times(1)).findById(pedidoId);
    }

    @Test
    void testRollBackEstoque() {
        PagamentoDTO pagamentoDTO = new PagamentoDTO();
        when(cancelarBaixaEstoqueUseCase.execute(pagamentoDTO)).thenReturn(pagamentoDTO);

        PagamentoDTO response = controller.rollBackEstoque(pagamentoDTO);

        assertEquals(pagamentoDTO, response);
        verify(cancelarBaixaEstoqueUseCase, times(1)).execute(pagamentoDTO);
    }
}
