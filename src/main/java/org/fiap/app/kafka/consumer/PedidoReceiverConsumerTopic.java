package org.fiap.app.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.fiap.app.service.ClienteGatewayService;
import org.fiap.app.service.ProdutoGatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PedidoReceiverConsumerTopic {

    @Autowired
    ClienteGatewayService clienteGatewayService;
    @Autowired
    ProdutoGatewayService produtoGatewayService;

    @KafkaListener(topics = "${spring.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String message, Acknowledgment acknowledgment) throws JsonProcessingException {
        acknowledgment.acknowledge();
        var resultado = clienteGatewayService.findByCpf("22553344888");
        var resultadoB = produtoGatewayService.findBySku("123456789");
        System.out.println(resultado.getCpf());
        System.out.println(resultadoB.getSku());
        log.info("PEDIDO LIDO COM SUCESSO");
    }
}
