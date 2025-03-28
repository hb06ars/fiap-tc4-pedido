package org.fiap.infra.config.kafka;

import org.junit.jupiter.api.Test;

class KafkaTopicCreatorTest {
    KafkaTopicCreator kafkaTopicCreator = new KafkaTopicCreator("bootstrapServers", "topic");

    @Test
    void testCreatorTopic() {
        kafkaTopicCreator.creatorTopic();
    }
}

