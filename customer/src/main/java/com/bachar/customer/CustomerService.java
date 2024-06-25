package com.bachar.customer;

import com.bachar.clients.fraud.FraudCheckResponse;
import com.bachar.clients.fraud.FraudClient;
import com.bachar.clients.notification.NotificationClient;
import com.bachar.clients.notification.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;
    private final FraudClient fraudClient;
    private final NotificationClient notificationClient;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        //todo: verify valid email
        //todo: check if email not taken

        Customer newCustomer = customerRepository.saveAndFlush(customer);

        //todo: check if fraudster
//        FraudCheckResponse isFraudster = restTemplate.getForObject("http://FRAUD/api/v1/fraud-check/{customerId}", FraudCheckResponse.class, newCustomer.getId());
        FraudCheckResponse isFraudster = fraudClient.isFraudster(newCustomer.getId());

        if (isFraudster != null && isFraudster.isFraudster()) {
            throw new IllegalStateException("He is Fraudster");
        }
        //todo: send notification
        notificationClient.sendNotification(NotificationRequest.builder()
                .toCustomerId(newCustomer.getId())
                .toCustomerEmail(newCustomer.getEmail())
                .message("Welcome in our Microservices Course").build());
    }
}
