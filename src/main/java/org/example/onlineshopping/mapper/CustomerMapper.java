package org.example.onlineshopping.mapper;

import org.example.onlineshopping.entity.Customer;
import org.example.onlineshopping.request.CustomerRequest;
import org.example.onlineshopping.response.CustomerResponse;
import org.mapstruct.Mapper;

import java.net.CacheRequest;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerRequest toDto(Customer customer);

    Customer toEntity(CustomerResponse response);

    List<CustomerRequest>toCustomerRequest(List<Customer>customers);
}
