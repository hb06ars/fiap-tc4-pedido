package org.fiap.domain.useCase.impl;

import org.fiap.app.service.gateway.ClienteGatewayService;
import org.fiap.app.service.gateway.PagamentoGatewayService;
import org.fiap.app.service.gateway.ProdutoGatewayService;
import org.fiap.app.service.postgres.PedidoService;
import org.fiap.domain.dto.ClienteDTO;
import org.fiap.domain.dto.ItensPedidoDTO;
import org.fiap.domain.dto.PedidoDTO;
import org.fiap.domain.dto.ProdutoDTO;
import org.fiap.domain.entity.PedidoEntity;
import org.fiap.domain.enums.StatusPagamentoEnum;
import org.fiap.domain.mapper.PedidoMapper;
import org.fiap.domain.usecase.CalcularTotalPedidoUseCase;
import org.fiap.domain.usecase.CancelarBaixaEstoqueUseCase;
import org.fiap.domain.usecase.EfetuarBaixaEstoqueUseCase;
import org.fiap.domain.usecase.SalvarPedidoUseCase;
import org.fiap.domain.usecase.ValidarEstoqueUseCase;
import org.fiap.domain.usecase.impl.ProcessarPedidoUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProcessarPedidoUseCaseImplTest {

    @InjectMocks
    private ProcessarPedidoUseCaseImpl processarPedidoUseCase;

    @Mock
    private ClienteGatewayService clienteGatewayService;

    @Mock
    private ProdutoGatewayService produtoGatewayService;

    @Mock
    private PagamentoGatewayService pagamentoGatewayService;

    @Mock
    private ValidarEstoqueUseCase validarEstoqueUseCase;

    @Mock
    private EfetuarBaixaEstoqueUseCase efetuarBaixaEstoqueUseCase;

    @Mock
    private CalcularTotalPedidoUseCase calcularTotalPedidoUseCase;

    @Mock
    private CancelarBaixaEstoqueUseCase cancelarBaixaEstoqueUseCase;

    @Mock
    private SalvarPedidoUseCase salvarPedidoUseCase;

    @Mock
    private PedidoService pedidoService;

    @Mock
    private PedidoMapper pedidoMapper;

    private PedidoDTO pedidoDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Criando um PedidoDTO de exemplo com itens
        pedidoDTO = PedidoDTO.builder()
                .clienteId(1L)
                .itensPedidoList(Arrays.asList(
                        ItensPedidoDTO.builder().skuProduto("sku1").quantidade(5).build(),
                        ItensPedidoDTO.builder().skuProduto("sku2").quantidade(3).build()
                ))
                .build();
    }

    @Test
    void testProcessarPedido_Success() {
        // Configurando mocks
        ClienteDTO cliente = new ClienteDTO();
        ProdutoDTO produto1 = ProdutoDTO.builder().sku("sku1").preco(BigDecimal.TEN).build();
        ProdutoDTO produto2 = ProdutoDTO.builder().sku("sku2").preco(BigDecimal.valueOf(20)).build();

        when(clienteGatewayService.findById(pedidoDTO.getClienteId())).thenReturn(cliente);
        when(produtoGatewayService.findBySku("sku1")).thenReturn(produto1);
        when(produtoGatewayService.findBySku("sku2")).thenReturn(produto2);
        when(calcularTotalPedidoUseCase.execute(pedidoDTO, Arrays.asList(produto1, produto2)))
                .thenReturn(BigDecimal.valueOf(160));
        when(validarEstoqueUseCase.execute(any())).thenReturn(true);

        // Chamando o método a ser testado
        processarPedidoUseCase.execute(pedidoDTO);

        // Verificando as interações e as chamadas dos métodos
        verify(clienteGatewayService).findById(pedidoDTO.getClienteId());
        verify(produtoGatewayService, times(2)).findBySku(any());
        verify(calcularTotalPedidoUseCase).execute(any(), any());
        verify(efetuarBaixaEstoqueUseCase).execute(any());
        verify(salvarPedidoUseCase).execute(any());
        verify(pagamentoGatewayService).save(any());
        verify(pedidoService).save(any(PedidoEntity.class));

        // Verificando o status do pedido
        assert pedidoDTO.getStatus() == StatusPagamentoEnum.FECHADO_SEM_CREDITO;
    }

    @Test
    void testProcessarPedido_PagamentoIndisponivel() {
        // Configurando mocks
        ClienteDTO cliente = new ClienteDTO();
        ProdutoDTO produto1 = ProdutoDTO.builder().sku("sku1").preco(BigDecimal.TEN).build();
        ProdutoDTO produto2 = ProdutoDTO.builder().sku("sku2").preco(BigDecimal.valueOf(20)).build();

        when(clienteGatewayService.findById(pedidoDTO.getClienteId())).thenReturn(cliente);
        when(produtoGatewayService.findBySku("sku1")).thenReturn(produto1);
        when(produtoGatewayService.findBySku("sku2")).thenReturn(produto2);
        when(calcularTotalPedidoUseCase.execute(pedidoDTO, Arrays.asList(produto1, produto2)))
                .thenReturn(BigDecimal.valueOf(160));
        when(validarEstoqueUseCase.execute(any())).thenReturn(true);
        doThrow(new RuntimeException("Pagamento Indisponível")).when(pagamentoGatewayService).save(any());

        // Chamando o método a ser testado
        processarPedidoUseCase.execute(pedidoDTO);

        // Verificando as interações e as chamadas dos métodos
        verify(pagamentoGatewayService).save(any());
        verify(cancelarBaixaEstoqueUseCase).execute(any());

        // Verificando o status do pedido
        assert pedidoDTO.getStatus() == StatusPagamentoEnum.ERRO_NA_API;
    }
}
