package org.example.onlineshopping.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.onlineshopping.entity.Product;
import org.example.onlineshopping.mapper.ProductMapper;
import org.example.onlineshopping.repository.ProductRepository;
import org.example.onlineshopping.request.ProductRequest;
import org.example.onlineshopping.response.ApiResponse;
import org.example.onlineshopping.response.ProductResponse;
import org.example.onlineshopping.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ApiResponse<ProductRequest> create(ProductResponse response) {
        Product entity = this.productMapper.toEntity(response);
        Double totalPrice = response.getPrice() * response.getTotalAmount();
        entity.setTotalPrice(totalPrice);
        Product save = this.productRepository.save(entity);
        ProductRequest request = this.productMapper.toDto(save);
        return ApiResponse.<ProductRequest>builder()
                .message("Product successfully created")
                .status(HttpStatus.CREATED)
                .data(request)
                .build();
    }

    @Override
    public ApiResponse<ProductRequest> get(Integer id) {
        Optional<Product> optional = this.productRepository.findById(id);
        if (optional.isPresent()) {
            Product product = optional.get();
            ProductRequest request = this.productMapper.toDto(product);
            return ApiResponse.<ProductRequest>builder()
                    .message("SUCCESS")
                    .status(HttpStatus.OK)
                    .data(request)
                    .build();
        }
        return ApiResponse.<ProductRequest>builder()
                .message("Product is not found")
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

    @Override
    public ApiResponse<String> update(Integer id, ProductResponse response) {
        Optional<Product> optional = this.productRepository.findById(id);
        if (optional.isPresent()) {
            Product product = optional.get();
            this.productMapper.update(product, response);
            product.setTotalPrice(response.getPrice() * response.getTotalAmount());
            this.productRepository.save(product);
            return ApiResponse.<String>builder()
                    .message("UPDATE successfully")
                    .status(HttpStatus.OK)
                    .build();
        }
        return ApiResponse.<String>builder()
                .message("product is not found")
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

    @Override
    public ApiResponse<String> delete(Integer id) {
        Optional<Product> optional = this.productRepository.findById(id);
        if (optional.isPresent()) {
            Product product = optional.get();
            this.productRepository.delete(product);
            return ApiResponse.<String>builder()
                    .message("DELETE successfully")
                    .status(HttpStatus.OK)
                    .build();
        }
        return ApiResponse.<String>builder()
                .message("product is not found")
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

    @Override
    public ApiResponse<List<ProductRequest>> getAll() {
        List<Product> products = this.productRepository.findAll();
        if (!products.isEmpty()) {
            List<ProductRequest> requests = this.productMapper.getAll(products);
            return ApiResponse.<List<ProductRequest>>builder()
                    .message("GET ALL PRODUCT")
                    .status(HttpStatus.ACCEPTED)
                    .data(requests)
                    .build();
        }
        return ApiResponse.<List<ProductRequest>>builder()
                .message("PRODUCT IS EMPTY")
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }

    @Override
    public ApiResponse<ProductRequest> discount(Double discount, Integer id) {
        Optional<Product> optional = this.productRepository.findById(id);
        if (optional.isPresent()) {
            Product product = optional.get();
            product.setDiscount(discount);
            Double discountPrice = product.getPrice() - product.getPrice() / 100 * discount;
            product.setDiscountPrice(discountPrice);
            this.productRepository.save(product);
            ProductRequest request = this.productMapper.toDto(product);
            return ApiResponse.<ProductRequest>builder()
                    .message("SUCCESS")
                    .status(HttpStatus.OK)
                    .data(request)
                    .build();
        }
        return ApiResponse.<ProductRequest>builder()
                .message("PRODUCT IS NOT FOUND")
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }
}
