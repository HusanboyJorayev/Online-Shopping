package org.example.onlineshopping.mapper;

import lombok.RequiredArgsConstructor;
import org.example.onlineshopping.dto.OrderDto;
import org.example.onlineshopping.entity.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    public OrderDto toDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .amount(order.getAmount())
                .customerId(order.getCustomerId())
                .productId(order.getProductId())
                .totalPrice(order.getTotalPrice())
                .build();
    }

    public List<OrderDto> toDtoList(List<Order> orders) {
        List<OrderDto> list = new ArrayList<>();
        for (Order order : orders) {
            list.add(
                    new OrderDto(
                            order.getId(),
                            order.getCustomerId(),
                            order.getTotalPrice(),
                            order.getAmount(),
                            order.getProductId()
                    )
            );
        }
        return list;
    }
}
