package org.fiap.domain.useCase.impl;

import org.fiap.app.service.postgres.ItensPedidoService;
import org.fiap.app.service.postgres.PedidoService;
import org.fiap.domain.dto.ItensPedidoDTO;
import org.fiap.domain.dto.PedidoDTO;
import org.fiap.domain.entity.ItensPedidoEntity;
import org.fiap.domain.entity.PedidoEntity;
import org.fiap.domain.enums.StatusPagamentoEnum;
import org.fiap.domain.mapper.PedidoMapper;
import org.fiap.domain.usecase.impl.SalvarPedidoUseCaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SalvarPedidoUseCaseImplTest {

    @InjectMocks
    private SalvarPedidoUseCaseImpl salvarPedidoUseCase;

    @Mock
    private PedidoService pedidoService;

    @Mock
    private ItensPedidoService itensPedidoService;

    @Mock
    private PedidoMapper pedidoMapper;

    private PedidoDTO pedidoDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        pedidoDTO = PedidoDTO.builder()
                .id(1L)
                .valorTotal(BigDecimal.valueOf(150))
                .itensPedidoList(Arrays.asList(
                        ItensPedidoDTO.builder().skuProduto("sku1").quantidade(2).build(),
                        ItensPedidoDTO.builder().skuProduto("sku2").quantidade(3).build()
                ))
                .build();
    }

    @Test
    void testSalvarPedido_Success() {
        PedidoEntity pedidoEntity = new PedidoEntity();
        pedidoEntity.setId(1L);
        pedidoEntity.setValorTotal(BigDecimal.valueOf(150));
        pedidoEntity.setStatus(StatusPagamentoEnum.FECHADO_COM_SUCESSO.getDescricao());

        when(pedidoMapper.convertEntity(pedidoDTO)).thenReturn(pedidoEntity);
        when(pedidoService.save(pedidoEntity)).thenReturn(new PedidoDTO(pedidoEntity));

        ItensPedidoEntity itensPedidoEntity1 = new ItensPedidoEntity();
        itensPedidoEntity1.setId(1L);
        itensPedidoEntity1.setPedidoId(1L);
        itensPedidoEntity1.setSkuProduto("sku1");
        itensPedidoEntity1.setQuantidade(2);

        ItensPedidoEntity itensPedidoEntity2 = new ItensPedidoEntity();
        itensPedidoEntity2.setId(2L);
        itensPedidoEntity2.setPedidoId(1L);
        itensPedidoEntity2.setSkuProduto("sku2");
        itensPedidoEntity2.setQuantidade(3);

        when(itensPedidoService.save(any(ItensPedidoEntity.class)))
                .thenReturn(new ItensPedidoDTO(itensPedidoEntity1))
                .thenReturn(new ItensPedidoDTO(itensPedidoEntity2));

        PedidoDTO result = salvarPedidoUseCase.execute(pedidoDTO);

        verify(pedidoService).save(pedidoEntity);
        verify(itensPedidoService, times(2)).save(any(ItensPedidoEntity.class));

        assertEquals(1L, result.getItensPedidoList().get(0).getId());
        assertEquals(2L, result.getItensPedidoList().get(1).getId());

        assertEquals(BigDecimal.valueOf(150), result.getValorTotal());
        assertEquals(1L, result.getId());
    }
}
