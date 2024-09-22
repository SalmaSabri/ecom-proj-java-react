package cz.cvut.fel.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Kafka configuration class for creating Kafka topics.
 *
 * This class is responsible for defining and configuring Kafka topics required by the application.
 */
@Configuration
public class KafkaTopicConfig {

    /**
     * Defines a Kafka topic named "cart-topic" with one partition.
     *
     * This topic will be used for messaging related to the cart functionality in the application.
     *
     * @return a {@link NewTopic} object representing the "cart-topic" with the specified configuration.
     */
    @Bean
    public NewTopic cartTopic(){
        return TopicBuilder
                .name("cart-topic")
                .partitions(1)
                .build();
    }
}