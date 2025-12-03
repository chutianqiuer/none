package com.example.demo;

import org.springframework.data.repository.CrudRepository;

// 只需要继承 CrudRepository，Spring 会自动实现 CRUD 方法
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    // 可以在这里定义自定义查询，例如：
    // List<Customer> findByLastName(String lastName);
}
