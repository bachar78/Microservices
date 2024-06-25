package com.bachar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(
        basePackages = "com.bachar.clients"
)
public class CustomerApplication
{
    public static void main( String[] args )
    {

        SpringApplication.run(CustomerApplication.class, args);
    }
}
