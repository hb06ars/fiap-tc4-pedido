package org.fiap.app.service.postgres;

import org.fiap.domain.dto.ItensPedidoDTO;
import org.fiap.domain.entity.ItensPedidoEntity;
import org.fiap.infra.repository.ItensPedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItensPedidoServiceTest {

    @Mock
    private ItensPedidoRepository itensPedidoRepository;

    @InjectMocks
    private ItensPedidoService itensPedidoService;

    private ItensPedidoEntity mockEntity;
    private ItensPedidoDTO mockDTO;

    @BeforeEach
    void setUp() {
        mockEntity = new ItensPedidoEntity();
        mockEntity.setId(1L);
        mockEntity.setPedidoId(10L);
        mockEntity.setSkuProduto("20");
        mockEntity.setQuantidade(3);
        mockDTO = new ItensPedidoDTO(mockEntity);
    }

    @Test
    void testFindByPedidoId_Success() {
        when(itensPedidoRepository.findByPedidoId(10L)).thenReturn(List.of(mockEntity));

        List<ItensPedidoDTO> result = itensPedidoService.findByPedidoId(10L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(10L, result.get(0).getPedidoId());
        verify(itensPedidoRepository, times(1)).findByPedidoId(10L);
    }

    @Test
    void testFindByPedidoId_EmptyList() {
        when(itensPedidoRepository.findByPedidoId(99L)).thenReturn(List.of());

        List<ItensPedidoDTO> result = itensPedidoService.findByPedidoId(99L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(itensPedidoRepository, times(1)).findByPedidoId(99L);
    }

    @Test
    void testSave_Success() {
        when(itensPedidoRepository.save(mockEntity)).thenReturn(mockEntity);

        ItensPedidoDTO result = itensPedidoService.save(mockEntity);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(10L, result.getPedidoId());
        assertEquals(3, result.getQuantidade());
        verify(itensPedidoRepository, times(1)).save(mockEntity);
    }
}
