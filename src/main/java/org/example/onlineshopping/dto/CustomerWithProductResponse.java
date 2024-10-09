package org.example.onlineshopping.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.onlineshopping.request.ProductRequest;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerWithProductResponse {
    private Integer id;
    private Double amount;
    private String username;
    private Double price;
    private String name;
    private Double discount;
    private Double discountPrice;
    private Integer productId;
    private Double totalPrice;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ShortResponse{
        private Integer id;
        private String username;
        private List<OrderDto> productRequests;
    }
}
