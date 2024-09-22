package cz.cvut.fel.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Configuration class for Kafka topics.
 *
 * This class defines the necessary Kafka topics used in the application.
 */
@Configuration
public class KafkaTopicConfig {

    /**
     * Defines a new Kafka topic named "inventory-topic" with 1 partition.
     *
     * This topic will be used to communicate inventory-related messages.
     *
     * @return a {@link NewTopic} object representing the "inventory-topic".
     */
    @Bean
    public NewTopic paymentTopic(){
        return TopicBuilder
                .name("inventory-topic")
                .partitions(1)
                .build();
    }
}
