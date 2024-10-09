package org.example.onlineshopping.response;

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
public class ProductResponse {
    private String name;
    private Double price;
    private Double totalAmount;
    private Double discountPrice = 0.0;
    private Double discount = 0.0;
}
