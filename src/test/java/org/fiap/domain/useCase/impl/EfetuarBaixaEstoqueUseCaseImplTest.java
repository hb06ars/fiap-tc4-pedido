package org.fiap.domain.usecase.impl;

import org.fiap.app.gateway.GatewayApi;
import org.fiap.app.rest.request.estoque.EstoqueRequest;
import org.fiap.app.service.gateway.EstoqueGatewayService;
import org.fiap.domain.dto.EstoqueDTO;
import org.fiap.domain.dto.ItensPedidoDTO;
import org.fiap.domain.dto.PedidoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EfetuarBaixaEstoqueUseCaseImplTest {

    @InjectMocks
    private EfetuarBaixaEstoqueUseCaseImpl efetuarBaixaEstoqueUseCase;

    @Mock
    private EstoqueGatewayService estoqueGatewayService;

    @Mock
    private GatewayApi gatewayApi;

    private PedidoDTO pedidoDTO;
    private EstoqueRequest estoqueRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        pedidoDTO = PedidoDTO.builder()
                .itensPedidoList(Arrays.asList(
                        ItensPedidoDTO.builder().produtoId(1L).quantidade(5).build(),
                        ItensPedidoDTO.builder().produtoId(2L).quantidade(3).build()
                ))
                .build();

        estoqueRequest = EstoqueRequest.builder().id(1L).quantidade(5).build();
    }

    @Test
    void testEfetuarBaixaEstoque() {
        when(gatewayApi.estoqueUpdateByIdProduto(any()))
                .thenReturn(EstoqueDTO.builder()
                        .quantidade(estoqueRequest.getQuantidade())
                        .id(estoqueRequest.getId())
                        .build());
        efetuarBaixaEstoqueUseCase.execute(pedidoDTO);
    }

    @Test
    void testEfetuarBaixaEstoqueComNenhumItem() {
        PedidoDTO pedidoVazio = PedidoDTO.builder().itensPedidoList(Arrays.asList()).build();
        efetuarBaixaEstoqueUseCase.execute(pedidoVazio);
        verify(estoqueGatewayService, times(0)).updateByIdProduto(any(EstoqueRequest.class));
    }
}
