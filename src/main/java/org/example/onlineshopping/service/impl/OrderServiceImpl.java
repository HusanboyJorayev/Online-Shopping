package org.example.onlineshopping.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.onlineshopping.dto.OrderDto;
import org.example.onlineshopping.entity.Order;
import org.example.onlineshopping.mapper.OrderMapper;
import org.example.onlineshopping.repository.OrderRepository;
import org.example.onlineshopping.response.ApiResponse;
import org.example.onlineshopping.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public ApiResponse<?> get(Integer id) {
        Optional<Order> optional = this.orderRepository.findById(id);
        if (optional.isPresent()) {
            Order order = optional.get();
            return ApiResponse.builder()
                    .message("OK")
                    .status(HttpStatus.OK)
                    .data(this.orderMapper.toDto(order))
                    .build();
        }
        return ApiResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .message("Order is not found")
                .build();
    }

    @Override
    public ApiResponse<?> delete(Integer id) {
        if (this.orderRepository.findById(id).isPresent()) {
            this.orderRepository.deleteById(id);
            return ApiResponse.builder()
                    .status(HttpStatus.OK)
                    .message("Deleted")
                    .build();
        }
        return ApiResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .message("Order is not found")
                .build();
    }

    @Override
    public ApiResponse<?> getAll() {
        List<Order> all = this.orderRepository.findAll();
        if (!all.isEmpty()) {
            List<OrderDto> dtoList = this.orderMapper.toDtoList(all);
            return ApiResponse.builder()
                    .status(HttpStatus.OK)
                    .message("OK")
                    .data(dtoList)
                    .build();
        }
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("List is empty")
                .build();
    }
}
