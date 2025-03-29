package org.fiap.domain.usecase.impl;

import org.fiap.app.service.gateway.ProdutoGatewayService;
import org.fiap.domain.dto.ItensPedidoDTO;
import org.fiap.domain.dto.PedidoDTO;
import org.fiap.domain.dto.ProdutoDTO;
import org.fiap.domain.enums.StatusPagamentoEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CalcularTotalPedidoUseCaseImplTest {

    @InjectMocks
    private CalcularTotalPedidoUseCaseImpl calcularTotalPedidoUseCase;

    @Mock
    private ProdutoGatewayService produtoGatewayService;

    private PedidoDTO pedidoDTO;
    private List<ProdutoDTO> produtos;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        pedidoDTO = PedidoDTO.builder()
                .id(1L)
                .clienteId(1L)
                .numeroCartaoCredito("1234567890123456")
                .status(StatusPagamentoEnum.ABERTO)
                .valorTotal(BigDecimal.ZERO)
                .itensPedidoList(List.of(ItensPedidoDTO.builder()
                        .skuProduto("123")
                        .quantidade(2)
                        .pedidoId(1L)
                        .build()))
                .build();

        ProdutoDTO produto1 = ProdutoDTO.builder().sku("123").preco(BigDecimal.valueOf(50.0)).build();

        produtos = Arrays.asList(produto1);
    }

    @Test
    void testCalcularTotalPedido() {
        // Configurando o mock do produtoGatewayService
        when(produtoGatewayService.findBySku("123")).thenReturn(produtos.get(0));

        BigDecimal totalPedido = calcularTotalPedidoUseCase.execute(pedidoDTO, produtos);

        assertNotNull(totalPedido);
        assertEquals(BigDecimal.valueOf(100.0), totalPedido);

        verify(produtoGatewayService, times(1)).findBySku("123");
    }

    @Test
    void testCalcularTotalPedidoComProdutoNaoEncontrado() {
        when(produtoGatewayService.findBySku("123")).thenReturn(null);

        assertThrows(NullPointerException.class, () -> {
            calcularTotalPedidoUseCase.execute(pedidoDTO, produtos);
        });

        verify(produtoGatewayService, times(1)).findBySku("123");
    }
}
