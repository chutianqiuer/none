package com.example.demo;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    // Spring Data JPA 会自动根据方法名生成 SQL
    List<Customer> findByLastName(String lastName);
}
