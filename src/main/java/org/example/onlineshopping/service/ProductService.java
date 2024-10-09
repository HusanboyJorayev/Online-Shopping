package org.example.onlineshopping.service;

import org.example.onlineshopping.entity.Product;
import org.example.onlineshopping.request.ProductRequest;
import org.example.onlineshopping.response.ApiResponse;
import org.example.onlineshopping.response.ProductResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProductService {
    ApiResponse<ProductRequest> create(ProductResponse data);

    ApiResponse<ProductRequest> get(Integer id);

    ApiResponse<String> update(Integer id, ProductResponse response);

    ApiResponse<String> delete(Integer id);

    ApiResponse<List<ProductRequest>> getAll();

    ApiResponse<ProductRequest>discount(Double discount,Integer id);
}
