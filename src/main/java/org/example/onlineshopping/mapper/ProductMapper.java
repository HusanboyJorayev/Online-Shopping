package org.example.onlineshopping.mapper;

import org.example.onlineshopping.entity.Product;
import org.example.onlineshopping.request.ProductRequest;
import org.example.onlineshopping.response.ProductResponse;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductRequest toDto(Product product);

    @Mapping(ignore = true, target = "id")
    Product toEntity(ProductResponse response);

    List<ProductRequest> getAll(List<Product> products);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget Product product, ProductResponse response);
}
