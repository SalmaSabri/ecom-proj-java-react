package cz.cvut.fel.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Kafka configuration class for creating topics.
 *
 * This class defines the Kafka topics that will be used in the application. Each topic
 * is created with a specific name and partition configuration.
 */
@Configuration
public class KafkaTopicConfig {

    /**
     * Creates a Kafka topic named "order-topic" with 1 partition.
     *
     * This topic will be used for processing order-related messages.
     *
     * @return a {@link NewTopic} object representing the "order-topic".
     */
    @Bean
    public NewTopic orderTopic(){
        return TopicBuilder
                .name("order-topic")
                .partitions(1)
                .build();
    }

    /**
     * Creates a Kafka topic named "history-topic" with 1 partition.
     *
     * This topic will be used for processing order history-related messages.
     *
     * @return a {@link NewTopic} object representing the "history-topic".
     */
    @Bean
    public NewTopic historyTopic(){
        return TopicBuilder
                .name("history-topic")
                .partitions(1)
                .build();
    }
}
