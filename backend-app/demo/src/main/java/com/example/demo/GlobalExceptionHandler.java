package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleLogicException(RuntimeException ex) {
        Map<String, String> map = new HashMap<>();
        map.put("error", ex.getMessage());
        return map;
    }

    // 专门处理并发抢购失败
    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    @ResponseStatus(HttpStatus.CONFLICT) // 返回 409 冲突
    public Map<String, String> handleConcurrencyError(ObjectOptimisticLockingFailureException ex) {
        Map<String, String> map = new HashMap<>();
        map.put("error", "抢购失败！您慢了一步，库存已被其他人抢走。请重试。");
        return map;
    }
}
