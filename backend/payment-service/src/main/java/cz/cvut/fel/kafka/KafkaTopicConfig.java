package cz.cvut.fel.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Kafka configuration class for creating topics.
 *
 * This class defines the Kafka topics that will be used for payment-related events in the application.
 */
@Configuration
public class KafkaTopicConfig {

    /**
     * Creates a Kafka topic named "payment-topic" with one partition.
     *
     * This topic is used for processing payment events.
     *
     * @return a {@link NewTopic} representing the "payment-topic".
     */
    @Bean
    public NewTopic paymentTopic(){
        return TopicBuilder
                .name("payment-topic")
                .partitions(1)
                .build();
    }
}
