package org.fiap.app.service.postgres;

import org.fiap.domain.dto.PedidoDTO;
import org.fiap.domain.entity.PedidoEntity;
import org.fiap.domain.enums.StatusPagamentoEnum;
import org.fiap.infra.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    private PedidoEntity mockEntity;
    private PedidoDTO mockDTO;

    @BeforeEach
    void setUp() {
        mockEntity = new PedidoEntity();
        mockEntity.setId(1L);
        mockEntity.setClienteId(100L);
        mockEntity.setStatus(StatusPagamentoEnum.FECHADO_COM_SUCESSO.getDescricao());
        mockEntity.setValorTotal(BigDecimal.TEN.multiply(BigDecimal.valueOf(25)));

        mockDTO = new PedidoDTO(mockEntity);
    }

    @Test
    void testFindById_Success() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(mockEntity));

        PedidoDTO result = pedidoService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(100L, result.getClienteId());
        assertEquals(BigDecimal.valueOf(250), result.getValorTotal());
        verify(pedidoRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        when(pedidoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NullPointerException.class, () -> pedidoService.findById(99L));
        verify(pedidoRepository, times(1)).findById(99L);
    }

    @Test
    void testSave_Success() {
        when(pedidoRepository.save(mockEntity)).thenReturn(mockEntity);

        PedidoDTO result = pedidoService.save(mockEntity);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(100L, result.getClienteId());
        assertEquals(BigDecimal.valueOf(250), result.getValorTotal());
        verify(pedidoRepository, times(1)).save(mockEntity);
    }

    @Test
    void testDelete_Success() {
        pedidoService.delete(mockEntity);
        verify(pedidoRepository, times(1)).delete(mockEntity);
    }
}
