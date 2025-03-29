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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CancelarBaixaEstoqueUseCaseImplTest {

    @InjectMocks
    private CancelarBaixaEstoqueUseCaseImpl cancelarBaixaEstoqueUseCase;

    @Mock
    private ItensPedidoService itensPedidoService;

    @Mock
    private PedidoService pedidoService;

    @Mock
    private EstoqueGatewayService estoqueGatewayService;

    @Mock
    private ProdutoGatewayService produtoGatewayService;

    private PagamentoDTO pagamentoDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Criando um PagamentoDTO de exemplo
        pagamentoDTO = PagamentoDTO.builder()
                .pedidoId(1L)
                .statusPagamento(StatusPagamentoEnum.FECHADO_COM_SUCESSO)
                .build();
    }

    @Test
    void testCancelarBaixaEstoque() {
        // Preparando mocks para os itens do pedido
        ItensPedidoDTO item1 = new ItensPedidoDTO();
        item1.setSkuProduto("123");
        item1.setQuantidade(2);

        ItensPedidoDTO item2 = new ItensPedidoDTO();
        item2.setSkuProduto("456");
        item2.setQuantidade(3);

        List<ItensPedidoDTO> itensPedido = Arrays.asList(item1, item2);

        // Configurando o mock do ItensPedidoService
        when(itensPedidoService.findByPedidoId(1L)).thenReturn(itensPedido);

        // Configurando o mock do ProdutoGatewayService
        when(produtoGatewayService.findBySku("123")).thenReturn(ProdutoDTO.builder().id(1L).build());
        when(produtoGatewayService.findBySku("456")).thenReturn(ProdutoDTO.builder().id(2L).build());

        // Configurando o mock do EstoqueGatewayService
        when(estoqueGatewayService.findByIdProduto(1L)).thenReturn(EstoqueDTO.builder().quantidade(10).build());
        when(estoqueGatewayService.findByIdProduto(2L)).thenReturn(EstoqueDTO.builder().quantidade(20).build());

        // Configurando o mock do PedidoService
        PedidoEntity pedidoEntity = new PedidoEntity();
        pedidoEntity.setId(1L);
        pedidoEntity.setStatus(StatusPagamentoEnum.FECHADO_COM_SUCESSO.getDescricao());
        when(pedidoService.findById(1L)).thenReturn(new PedidoDTO(pedidoEntity));

        // Executando o método
        PagamentoDTO result = cancelarBaixaEstoqueUseCase.execute(pagamentoDTO);

        // Verificando as interações com os mocks
        verify(itensPedidoService, times(1)).findByPedidoId(1L);
        verify(produtoGatewayService, times(1)).findBySku("123");
        verify(produtoGatewayService, times(1)).findBySku("456");
        verify(estoqueGatewayService, times(1)).findByIdProduto(1L);
        verify(estoqueGatewayService, times(1)).findByIdProduto(2L);
        verify(estoqueGatewayService, times(2)).updateByIdProduto(any(EstoqueRequest.class));
        verify(pedidoService, times(1)).save(any(PedidoEntity.class));

        // Verificando o retorno
        assertNotNull(result);
        assertEquals(StatusPagamentoEnum.FECHADO_COM_SUCESSO, result.getStatusPagamento());

        // Verificando o status do pedido
        assertEquals(StatusPagamentoEnum.FECHADO_COM_SUCESSO.getDescricao(), pedidoEntity.getStatus());
    }

}
