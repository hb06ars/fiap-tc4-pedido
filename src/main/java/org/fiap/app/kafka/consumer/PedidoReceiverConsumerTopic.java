package org.fiap.app.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.fiap.domain.dto.PedidoDTO;
import org.fiap.domain.usecase.ProcessarPedidoUseCase;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PedidoReceiverConsumerTopic {

    private final ProcessarPedidoUseCase processarPedidoUseCase;
    private final ObjectMapper objectMapper;

    public PedidoReceiverConsumerTopic(ProcessarPedidoUseCase processarPedidoUseCase, ObjectMapper objectMapper) {
        this.processarPedidoUseCase = processarPedidoUseCase;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "${spring.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String message, Acknowledgment acknowledgment) throws JsonProcessingException {
        log.info("Processando pedido: ".concat(message));
        PedidoDTO pedidoDTO = objectMapper.readValue(message, PedidoDTO.class);
        processarPedidoUseCase.execute(pedidoDTO);
        acknowledgment.acknowledge();
        log.info("Consumo Finalizado");
    }
}
