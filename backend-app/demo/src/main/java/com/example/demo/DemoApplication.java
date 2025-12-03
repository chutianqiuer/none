package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import java.math.BigDecimal;

@SpringBootApplication
@EnableScheduling
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    // 这里的参数必须能被 Spring 容器找到
    @Bean
    public CommandLineRunner demo(CustomerRepository customerRepo, ProductRepository productRepo) {
        return (args) -> {
            // 为了防止重复数据导致重启报错，先清理一下（可选）
            // customerRepo.deleteAll();
            // productRepo.deleteAll();

            if (customerRepo.count() == 0) {
                customerRepo.save(new Customer("Jack", "Ma"));
                customerRepo.save(new Customer("Elon", "Musk"));
            }

            if (productRepo.count() == 0) {
                productRepo.save(new Product("iPhone 15 Pro", new BigDecimal("999.99"), 5));
                productRepo.save(new Product("MacBook Air", new BigDecimal("1299.00"), 100));
            }

            System.out.println("--- 系统启动成功：数据已验证/初始化 ---");
        };
    }
}
