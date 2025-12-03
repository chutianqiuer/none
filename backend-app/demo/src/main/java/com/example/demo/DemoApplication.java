package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(CustomerRepository customerRepo) {
        return (args) -> {
            // 初始化两个客户
            customerRepo.save(new Customer("Jack", "Ma"));
            customerRepo.save(new Customer("Elon", "Musk"));
            System.out.println("--- 基础客户数据已初始化 (ID 1 & 2) ---");
        };
    }
}
