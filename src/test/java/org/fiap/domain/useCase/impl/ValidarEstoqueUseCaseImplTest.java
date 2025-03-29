package org.fiap.domain.useCase.impl;

import org.fiap.domain.dto.ItensPedidoDTO;
import org.fiap.domain.dto.PedidoDTO;
import org.fiap.domain.usecase.impl.ValidarEstoqueUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ValidarEstoqueUseCaseImplTest {

    @InjectMocks
    private ValidarEstoqueUseCaseImpl validarEstoqueUseCase;

    @Mock
    private PedidoDTO pedidoDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEstoqueValido() {
        ItensPedidoDTO item1 = ItensPedidoDTO.builder().skuProduto("sku1").quantidade(5).build();
        ItensPedidoDTO item2 = ItensPedidoDTO.builder().skuProduto("sku2").quantidade(3).build();

        when(pedidoDTO.getItensPedidoList()).thenReturn(java.util.List.of(item1, item2));

        boolean result = validarEstoqueUseCase.execute(pedidoDTO);

        assertTrue(result);
        verify(pedidoDTO).getItensPedidoList();
    }

    @Test
    void testEstoqueInvalido() {
        ItensPedidoDTO item1 = ItensPedidoDTO.builder().skuProduto("sku1").quantidade(5).build();
        ItensPedidoDTO item2 = ItensPedidoDTO.builder().skuProduto("sku2").quantidade(-3).build();  // Quantidade negativa

        when(pedidoDTO.getItensPedidoList()).thenReturn(java.util.List.of(item1, item2));

        boolean result = validarEstoqueUseCase.execute(pedidoDTO);

        assertFalse(result);
        verify(pedidoDTO).getItensPedidoList();
    }
}
