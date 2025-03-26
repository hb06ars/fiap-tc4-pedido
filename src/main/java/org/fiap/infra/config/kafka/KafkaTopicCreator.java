package org.fiap.infra.config.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.KafkaFuture;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Configuration
@Slf4j
public class KafkaTopicCreator {

    private final String BOOTSTRAP_SERVERS;
    private final String TOPIC;

    public KafkaTopicCreator(
            @Value("${spring.kafka.bootstrap-servers}") String bootstrapServers,
            @Value("${spring.kafka.topic}") String topic
    ) {
        BOOTSTRAP_SERVERS = bootstrapServers;
        TOPIC = topic;
    }


    public void creatorTopic() {

        Properties config = new Properties();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);

        try (AdminClient adminClient = AdminClient.create(config)) {
            if (!topicExists(adminClient, TOPIC)) {
                NewTopic newTopic = new NewTopic(TOPIC, 1, (short) 1);
                adminClient.createTopics(Collections.singletonList(newTopic)).all().get();
                log.info("Tópico criado: " + TOPIC);
            } else {
                log.error("Tópico já existe: " + TOPIC);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean topicExists(AdminClient adminClient, String topicName) throws ExecutionException, InterruptedException {
        KafkaFuture<Boolean> exists = adminClient.listTopics().names().thenApply(names -> names.contains(topicName));
        return exists.get();
    }
}
