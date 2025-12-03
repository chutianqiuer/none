package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import java.math.BigDecimal;

@SpringBootApplication
@EnableScheduling // 开启定时任务开关
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(CustomerRepository customerRepo, ProductRepository productRepo) {
        return (args) -> {
            // 初始化客户
            customerRepo.save(new Customer("Jack", "Ma"));

            // 初始化商品 (库存很少，方便测试并发)
            productRepo.save(new Product("iPhone 15 Pro", new BigDecimal("999.99"), 5)); // 只有5台
            productRepo.save(new Product("MacBook Air", new BigDecimal("1299.00"), 100));

            System.out.println("--- 数据初始化完成：iPhone库存仅5台 ---");
        };
    }
}
