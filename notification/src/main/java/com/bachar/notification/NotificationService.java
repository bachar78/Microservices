package com.bachar.notification;

import com.bachar.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void send(NotificationRequest notification) {
        notificationRepository.save(
                Notification.builder()
                        .toCustomerId(notification.toCustomerId())
                        .toCustomerEmail(notification.toCustomerEmail())
                        .sender("bachar@github.com")
                        .message(notification.message())
                        .sentAt(LocalDateTime.now())
                        .build()
        );
    }
}
