package cz.cvut.fel.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic orderTopic(){
        return TopicBuilder
                .name("order-topic")
                .partitions(1)
                .build();
    }

    @Bean
    public NewTopic historyTopic(){
        return TopicBuilder
                .name("history-topic")
                .partitions(1)
                .build();
    }
}
