package dev.kbe.produktbackend.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {
    public static final String QUEUE_NAME = "product-queue";

    @Bean
    public Queue productQueue() {
        return new Queue(QUEUE_NAME, false);
    }
}
