package org.fiap.domain.useCase.impl;

import org.fiap.app.service.gateway.ProdutoGatewayService;
import org.fiap.domain.dto.ItensPedidoDTO;
import org.fiap.domain.dto.PedidoDTO;
import org.fiap.domain.dto.ProdutoDTO;
import org.fiap.domain.usecase.impl.CalcularTotalPedidoUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CalcularTotalPedidoUseCaseImplTest {

    @InjectMocks
    private CalcularTotalPedidoUseCaseImpl calcularTotalPedidoUseCase;

    @Mock
    private ProdutoGatewayService produtoGatewayService;

    @Mock
    private PedidoDTO pedidoDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalcularTotalPedido() {
        // Criando o produto com preço
        ProdutoDTO produto1 = ProdutoDTO.builder().sku("sku1").preco(BigDecimal.valueOf(100)).build();
        ProdutoDTO produto2 = ProdutoDTO.builder().sku("sku2").preco(BigDecimal.valueOf(50)).build();

        // Criando os itens do pedido com quantidade
        ItensPedidoDTO item1 = ItensPedidoDTO.builder().skuProduto("sku1").quantidade(2).build();
        ItensPedidoDTO item2 = ItensPedidoDTO.builder().skuProduto("sku2").quantidade(3).build();

        // Simulando o retorno dos produtos no gateway
        when(produtoGatewayService.findBySku("sku1")).thenReturn(produto1);
        when(produtoGatewayService.findBySku("sku2")).thenReturn(produto2);

        // Simulando o retorno dos itens no pedido
        when(pedidoDTO.getItensPedidoList()).thenReturn(List.of(item1, item2));

        // Chamando o método a ser testado
        BigDecimal totalPedido = calcularTotalPedidoUseCase.execute(pedidoDTO, List.of(produto1, produto2));

        // Verificando o valor total
        BigDecimal valorEsperado = (produto1.getPreco().multiply(BigDecimal.valueOf(2)))  // 100 * 2
                .add(produto2.getPreco().multiply(BigDecimal.valueOf(3)));  // 50 * 3

        assertEquals(valorEsperado, totalPedido);
        verify(produtoGatewayService).findBySku("sku1");
        verify(produtoGatewayService).findBySku("sku2");
    }

}


