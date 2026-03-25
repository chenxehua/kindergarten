package com.kgms.record;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class RecordApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecordApplication.class, args);
    }
}
