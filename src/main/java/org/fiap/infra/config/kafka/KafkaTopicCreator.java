package org.fiap.infra.config.kafka;

import jakarta.annotation.PostConstruct;
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
    private final String TOPIC_DLQ;

    public KafkaTopicCreator(
            @Value("${spring.kafka.bootstrap-servers}") String bootstrapServers,
            @Value("${spring.kafka.topic}") String topic,
            @Value("${spring.kafka.topic-dlq}") String topicDlq
    ) {
        BOOTSTRAP_SERVERS = bootstrapServers;
        TOPIC = topic;
        TOPIC_DLQ = topicDlq;
    }

    @PostConstruct
    public void init() {
        creatorTopic();
    }

    public void creatorTopic() {

        Properties config = new Properties();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);

        try (AdminClient adminClient = AdminClient.create(config)) {
            criarTopico(adminClient, TOPIC);
            criarTopico(adminClient, TOPIC_DLQ);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void criarTopico(AdminClient adminClient, String topico) throws ExecutionException, InterruptedException {
        if (!topicExists(adminClient, topico)) {
            NewTopic newTopic = new NewTopic(topico, 1, (short) 1);
            adminClient.createTopics(Collections.singletonList(newTopic)).all().get();
            log.info("Tópico criado: " + topico);
        } else {
            log.error("Tópico já existe: " + topico);
        }
    }

    private static boolean topicExists(AdminClient adminClient, String topicName) throws ExecutionException, InterruptedException {
        KafkaFuture<Boolean> exists = adminClient.listTopics().names().thenApply(names -> names.contains(topicName));
        return exists.get();
    }
}
