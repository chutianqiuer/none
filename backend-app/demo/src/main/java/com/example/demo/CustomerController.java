package com.example.demo;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping
    public Iterable<Customer> findAll() {
        return service.findAll();
    }

    // 新增：根据 ID 查询
    @GetMapping("/{id}")
    public Customer findOne(@PathVariable Long id) {
        return service.findById(id);
    }

    // 新增：根据姓氏查询 (例如 /customers/search?lastName=Bauer)
    @GetMapping("/search")
    public List<Customer> search(@RequestParam String lastName) {
        return service.findByLastName(lastName);
    }

    // 更新：添加了 @Valid 进行数据校验
    @PostMapping
    public Customer addCustomer(@Valid @RequestBody Customer customer) {
        return service.save(customer);
    }

    // 新增：删除用户
    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        service.deleteById(id);
    }
}
