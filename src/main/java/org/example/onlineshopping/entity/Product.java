package org.example.onlineshopping.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Double price;  // Asl mahsulot narxi
    private Double totalAmount;  // Mahsulot miqdori
    private Double totalPrice;  // Umumiy narx (totalAmount * price)
    private Double discountPrice = 0.0;  // Chegirma qo'llanganidan keyingi narx
    private Double discount = 0.0;

    @OneToMany
    private List<Order> orders;
}
