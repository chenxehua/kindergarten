package com.kgms.growth;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
@EnableDiscoveryClient
@SpringBootApplication
public class GrowthApplication {
    public static void main(String[] args) { SpringApplication.run(GrowthApplication.class, args); }
}
