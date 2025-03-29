package org.fiap.domain.useCase.impl;

import org.fiap.app.rest.request.estoque.EstoqueRequest;
import org.fiap.app.service.gateway.EstoqueGatewayService;
import org.fiap.app.service.gateway.ProdutoGatewayService;
import org.fiap.app.service.postgres.ItensPedidoService;
import org.fiap.app.service.postgres.PedidoService;
import org.fiap.domain.dto.EstoqueDTO;
import org.fiap.domain.dto.ItensPedidoDTO;
import org.fiap.domain.dto.PagamentoDTO;
import org.fiap.domain.dto.PedidoDTO;
import org.fiap.domain.dto.ProdutoDTO;
import org.fiap.domain.entity.PedidoEntity;
import org.fiap.domain.enums.StatusPagamentoEnum;
import org.fiap.domain.usecase.impl.CancelarBaixaEstoqueUseCaseImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

class CancelarBaixaEstoqueUseCaseImplTest {
    @Mock
    ItensPedidoService itensPedidoService;
    @Mock
    PedidoService pedidoService;
    @Mock
    EstoqueGatewayService estoqueGatewayService;
    @Mock
    ProdutoGatewayService produtoGatewayService;
    @InjectMocks
    CancelarBaixaEstoqueUseCaseImpl cancelarBaixaEstoqueUseCaseImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute() {
        when(itensPedidoService.findByPedidoId(anyLong())).thenReturn(List.of(new ItensPedidoDTO(Long.valueOf(1), Long.valueOf(1), "skuProduto", Long.valueOf(1), Integer.valueOf(0), LocalDateTime.of(2025, Month.MARCH, 29, 0, 50, 24))));
        when(pedidoService.findById(anyLong())).thenReturn(new PedidoDTO(Long.valueOf(1), Long.valueOf(1), "numeroCartaoCredito", StatusPagamentoEnum.ABERTO, List.of(new ItensPedidoDTO(Long.valueOf(1), Long.valueOf(1), "skuProduto", Long.valueOf(1), Integer.valueOf(0), LocalDateTime.of(2025, Month.MARCH, 29, 0, 50, 24))), LocalDateTime.of(2025, Month.MARCH, 29, 0, 50, 24), LocalDateTime.of(2025, Month.MARCH, 29, 0, 50, 24), LocalDateTime.of(2025, Month.MARCH, 29, 0, 50, 24), new BigDecimal(0)));
        when(pedidoService.save(any(PedidoEntity.class))).thenReturn(new PedidoDTO(Long.valueOf(1), Long.valueOf(1), "numeroCartaoCredito", StatusPagamentoEnum.ABERTO, List.of(new ItensPedidoDTO(Long.valueOf(1), Long.valueOf(1), "skuProduto", Long.valueOf(1), Integer.valueOf(0), LocalDateTime.of(2025, Month.MARCH, 29, 0, 50, 24))), LocalDateTime.of(2025, Month.MARCH, 29, 0, 50, 24), LocalDateTime.of(2025, Month.MARCH, 29, 0, 50, 24), LocalDateTime.of(2025, Month.MARCH, 29, 0, 50, 24), new BigDecimal(0)));
        when(estoqueGatewayService.findByIdProduto(anyLong())).thenReturn(new EstoqueDTO(Long.valueOf(1), Long.valueOf(1), Integer.valueOf(0), LocalDateTime.of(2025, Month.MARCH, 29, 0, 50, 24)));
        when(estoqueGatewayService.updateByIdProduto(any(EstoqueRequest.class))).thenReturn(new EstoqueDTO(Long.valueOf(1), Long.valueOf(1), Integer.valueOf(0), LocalDateTime.of(2025, Month.MARCH, 29, 0, 50, 24)));
        when(produtoGatewayService.findBySku(anyString())).thenReturn(new ProdutoDTO(Long.valueOf(1), "nome", "sku", new BigDecimal(0), LocalDateTime.of(2025, Month.MARCH, 29, 0, 50, 24), LocalDateTime.of(2025, Month.MARCH, 29, 0, 50, 24)));

        PagamentoDTO result = cancelarBaixaEstoqueUseCaseImpl.execute(new PagamentoDTO(Long.valueOf(1), Long.valueOf(1), "idPagamento", LocalDateTime.of(2025, Month.MARCH, 29, 0, 50, 24), StatusPagamentoEnum.ABERTO, LocalDateTime.of(2025, Month.MARCH, 29, 0, 50, 24), new BigDecimal(0)));
        Assertions.assertEquals(new PagamentoDTO(Long.valueOf(1), Long.valueOf(1), "idPagamento", LocalDateTime.of(2025, Month.MARCH, 29, 0, 50, 24), StatusPagamentoEnum.ABERTO, LocalDateTime.of(2025, Month.MARCH, 29, 0, 50, 24), new BigDecimal(0)), result);
    }
}

