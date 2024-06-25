package com.bachar.notification.rabbitmq;

import com.bachar.clients.notification.NotificationRequest;
import com.bachar.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationService notificationService;

    @RabbitListener(queues = "${spring.rabbitmq.template.default-receive-queue}")
    public void consumer(NotificationRequest request) {
        log.info("Consumed notification {} from queue", request);
        notificationService.send(request);
    }


}
