package org.fiap.domain.useCase.impl;

import org.fiap.app.service.gateway.EstoqueGatewayService;
import org.fiap.domain.dto.ItensPedidoDTO;
import org.fiap.domain.dto.PedidoDTO;
import org.fiap.domain.usecase.impl.EfetuarBaixaEstoqueUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EfetuarBaixaEstoqueUseCaseImplTest {

    @InjectMocks
    private EfetuarBaixaEstoqueUseCaseImpl efetuarBaixaEstoqueUseCase;

    @Mock
    private EstoqueGatewayService estoqueGatewayService;

    @Mock
    private PedidoDTO pedidoDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEfetuarBaixaEstoque() {
        ItensPedidoDTO item1 = ItensPedidoDTO.builder().produtoId(1L).quantidade(2).build();
        ItensPedidoDTO item2 = ItensPedidoDTO.builder().produtoId(2L).quantidade(5).build();

        when(pedidoDTO.getItensPedidoList()).thenReturn(List.of(item1, item2));

        efetuarBaixaEstoqueUseCase.execute(pedidoDTO);

        verify(estoqueGatewayService, times(2)).updateByIdProduto(any());
    }

    @Test
    void testBaixaEstoqueComListaVazia() {
        when(pedidoDTO.getItensPedidoList()).thenReturn(List.of());
        efetuarBaixaEstoqueUseCase.execute(pedidoDTO);
        verify(estoqueGatewayService, never()).updateByIdProduto(any());
    }
}
