package com.bachar.notification;

import lombok.Getter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@Getter
public class NotificationConfig {

    @Value("${spring.rabbitmq.template.exchange}")
    private String internalExchange;

    @Value("${spring.rabbitmq.template.default-receive-queue}")
    private String notificationQueue;

    @Value("${spring.rabbitmq.template.routing-key}")
    private String internalNotificationRoutingKey;


    @Bean
    public TopicExchange internalExchange() {
        return new TopicExchange(this.internalExchange);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(this.notificationQueue);
    }

    @Bean
    public Binding interalNotificationBinding() {
        return BindingBuilder
                .bind(this.notificationQueue())
                .to(this.internalExchange())
                .with(this.internalNotificationRoutingKey);
    }
}
