package org.fiap.infra.config.kafka;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaAdmin;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class KafkaConfigTest {

    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String GROUP_ID = "test-group";
    private static final String TOPIC = "test-topic";

    @InjectMocks
    private KafkaConfig kafkaConfig = new KafkaConfig();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
//
//    @Test
//    void testConsumerFactory() {
//        // Verificar se o ConsumerFactory é criado corretamente
//        ConsumerFactory<String, String> consumerFactory = kafkaConfig.consumerFactory();
//        assertNotNull(consumerFactory);
//
//        Map<String, Object> config = consumerFactory.getConfigurationProperties();
//        assertEquals(BOOTSTRAP_SERVERS, config.get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG));
//        assertEquals(GROUP_ID, config.get(ConsumerConfig.GROUP_ID_CONFIG));
//        assertEquals(StringDeserializer.class, config.get(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG));
//        assertEquals(StringDeserializer.class, config.get(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG));
//        assertEquals("latest", config.get(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG));
//    }
//
//    @Test
//    void testKafkaListenerContainerFactory() {
//        // Verificar se o KafkaListenerContainerFactory é criado corretamente
//        ConcurrentKafkaListenerContainerFactory<String, String> factory = kafkaConfig.kafkaListenerContainerFactory();
//        assertNotNull(factory);
//        assertEquals(factory.getContainerProperties().getAckMode(), "MANUAL", "Modo de reconhecimento deve ser MANUAL.");
//    }

    @Test
    void testKafkaAdmin() {
        KafkaAdmin kafkaAdmin = kafkaConfig.kafkaAdmin();
        assertNotNull(kafkaAdmin);
    }
}
