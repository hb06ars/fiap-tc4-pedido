package org.fiap.domain.mapper;

import org.fiap.app.service.gateway.EstoqueGatewayService;
import org.fiap.domain.dto.EstoqueDTO;
import org.fiap.domain.dto.ItensPedidoDTO;
import org.fiap.domain.dto.PedidoDTO;
import org.fiap.domain.dto.ProdutoDTO;
import org.fiap.domain.entity.PedidoEntity;
import org.fiap.domain.enums.StatusPagamentoEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoMapperTest {

    @Mock
    private EstoqueGatewayService estoqueGatewayService;

    @InjectMocks
    private PedidoMapper pedidoMapper;

    private PedidoDTO pedidoDTO;
    private ProdutoDTO produtoDTO;
    private EstoqueDTO estoqueDTO;

    @BeforeEach
    void setUp() {
        pedidoDTO = PedidoDTO.builder()
                .id(1L)
                .clienteId(1L)
                .numeroCartaoCredito("1234567890123456")
                .status(StatusPagamentoEnum.ABERTO)
                .valorTotal(BigDecimal.valueOf(100.00))
                .build();

        produtoDTO = ProdutoDTO.builder()
                .id(1L)
                .sku("ABC123")
                .nome("Produto Teste")
                .build();

        estoqueDTO = EstoqueDTO.builder()
                .id(1L)
                .quantidade(100)
                .produtoId(produtoDTO.getId())
                .build();

        pedidoDTO.setItensPedidoList(Arrays.asList(
                ItensPedidoDTO.builder()
                        .skuProduto("ABC123")
                        .quantidade(40)
                        .build()
        ));
    }

    @Test
    void testEstoqueBaixaDto() {
        when(estoqueGatewayService.findByIdProduto(produtoDTO.getId())).thenReturn(estoqueDTO);

        PedidoDTO result = pedidoMapper.estoqueBaixaDto(Arrays.asList(produtoDTO), pedidoDTO);

        assertNotNull(result);
        assertEquals(60, result.getItensPedidoList().get(0).getQuantidade());
        verify(estoqueGatewayService, times(1)).findByIdProduto(produtoDTO.getId());
    }

    @Test
    void testConvertEntity() {
        PedidoEntity result = pedidoMapper.convertEntity(pedidoDTO);

        assertNotNull(result);
        assertEquals(pedidoDTO.getId(), result.getId());
        assertEquals(pedidoDTO.getNumeroCartaoCredito(), result.getNumeroCartaoCredito());
        assertEquals(pedidoDTO.getClienteId(), result.getClienteId());
        assertEquals(pedidoDTO.getStatus().getDescricao(), result.getStatus());
        assertEquals(pedidoDTO.getValorTotal(), result.getValorTotal());
    }
}
