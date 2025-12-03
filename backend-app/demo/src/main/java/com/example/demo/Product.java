package com.example.demo;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private BigDecimal price;
    private Integer stock;

    // 核心：乐观锁版本号。每次更新时，Hibernate 会自动检查版本是否一致。
    @Version
    private Integer version;

    protected Product() {}

    public Product(String name, BigDecimal price, Integer stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public Integer getStock() { return stock; }

    // 扣减库存逻辑
    public void decreaseStock(int quantity) {
        if (this.stock < quantity) {
            throw new RuntimeException("Out of stock: " + name);
        }
        this.stock -= quantity;
    }

    // 恢复库存逻辑
    public void increaseStock(int quantity) {
        this.stock += quantity;
    }
}
