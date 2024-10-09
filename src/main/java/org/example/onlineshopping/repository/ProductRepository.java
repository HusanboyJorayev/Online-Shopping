package org.example.onlineshopping.repository;

import org.example.onlineshopping.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

    @Query(value = "select * from product as p where p.id in (?1)", nativeQuery = true)
    List<Product> selectProductsById(List<Integer> productIds);
}
