package org.fiap.app.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.fiap.domain.dto.PedidoDTO;
import org.fiap.domain.usecase.ProcessarPedidoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.support.Acknowledgment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PedidoReceiverConsumerTopicTest {

    @Mock
    private ProcessarPedidoUseCase processarPedidoUseCase;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private Acknowledgment acknowledgment;

    @InjectMocks
    private PedidoReceiverConsumerTopic pedidoReceiverConsumerTopic;

    private static final String MOCK_MESSAGE = "{\"id\": 1, \"clienteId\": 123, \"valorTotal\": 99.90}";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConsumeSuccess() throws JsonProcessingException {
        // Arrange
        PedidoDTO pedidoMock = new PedidoDTO();
        when(objectMapper.readValue(MOCK_MESSAGE, PedidoDTO.class)).thenReturn(pedidoMock);

        // Act
        pedidoReceiverConsumerTopic.consume(MOCK_MESSAGE, acknowledgment);

        // Assert
        ArgumentCaptor<PedidoDTO> pedidoCaptor = ArgumentCaptor.forClass(PedidoDTO.class);
        verify(processarPedidoUseCase, times(1)).execute(pedidoCaptor.capture());
        assertEquals(pedidoMock, pedidoCaptor.getValue());

        verify(acknowledgment, times(1)).acknowledge();
    }

    @Test
    void testConsumeJsonProcessingException() throws JsonProcessingException {
        // Arrange
        when(objectMapper.readValue(MOCK_MESSAGE, PedidoDTO.class)).thenThrow(new JsonProcessingException("Erro ao processar JSON") {});

        // Act & Assert
        try {
            pedidoReceiverConsumerTopic.consume(MOCK_MESSAGE, acknowledgment);
        } catch (JsonProcessingException e) {
            assertEquals("Erro ao processar JSON", e.getMessage());
        }

        verify(processarPedidoUseCase, never()).execute(any());
        verify(acknowledgment, never()).acknowledge();
    }
}
