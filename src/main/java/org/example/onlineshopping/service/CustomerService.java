package org.example.onlineshopping.service;

import org.example.onlineshopping.request.CustomerRequest;
import org.example.onlineshopping.response.ApiResponse;
import org.example.onlineshopping.response.CustomerResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public interface CustomerService {
    ApiResponse<CustomerRequest> create(CustomerResponse response, List<Integer> productIds);

    ApiResponse<CustomerRequest> get(Integer id);

    ApiResponse<String> update(Integer id, CustomerResponse response);

    ApiResponse<String> delete(Integer id);

    ApiResponse<List<CustomerRequest>> getAll();

    ApiResponse<?> customerWithProductsById(Integer id);

    ApiResponse<?> customerWithOrdersById(Integer id);

    ApiResponse<?> filter(Integer id, String username, Double amount);
}
