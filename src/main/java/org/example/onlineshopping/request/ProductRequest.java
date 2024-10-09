package org.example.onlineshopping.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductRequest {
    private String name;
    private Double price;
    private Double totalAmount;
    private Double totalPrice;
    private Double discountPrice = 0.0;
    private Double discount = 0.0;
}
