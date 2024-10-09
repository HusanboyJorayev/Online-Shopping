package org.example.onlineshopping.service;

import org.example.onlineshopping.response.ApiResponse;
import org.springframework.stereotype.Component;

@Component
public interface OrderService {
    ApiResponse<?> get(Integer id);

    ApiResponse<?> delete(Integer id);

    ApiResponse<?> getAll();
}
