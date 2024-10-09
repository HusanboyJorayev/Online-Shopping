package org.example.onlineshopping.entity;

import jakarta.persistence.*;
import lombok.*;


import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private Double amount;

    @ManyToOne
    private Order orders;
}

