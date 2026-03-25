package com.kgms.classs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ClassApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClassApplication.class, args);
    }
}
