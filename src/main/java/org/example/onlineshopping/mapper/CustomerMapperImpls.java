package org.example.onlineshopping.mapper;

import lombok.RequiredArgsConstructor;
import org.example.onlineshopping.entity.Customer;
import org.example.onlineshopping.entity.Order;
import org.example.onlineshopping.entity.Product;
import org.example.onlineshopping.repository.CustomerRepository;
import org.example.onlineshopping.repository.OrderRepository;
import org.example.onlineshopping.repository.ProductRepository;
import org.example.onlineshopping.request.CustomerRequest;
import org.example.onlineshopping.response.CustomerResponse;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CustomerMapperImpls {
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public CustomerRequest toDto(Customer customer) {
        return CustomerRequest.builder()
                .username(customer.getUsername())
                .build();
    }

    public boolean updated(Integer id, CustomerResponse response) {
        Customer customer = this.customerRepository.findById(id)
                .orElse(null);
        if (customer != null) {
            customer.setUsername(response.getUsername());
            Order orders = customer.getOrders();
            if (orders != null) {
                Product product = this.productRepository.findById(orders.getProductId())
                        .orElse(null);
                if (product != null) {

                    orders.setCustomerId(customer.getId());
                    orders.setProductId(product.getId());
                    orders.setTotalPrice(response.getAmount() * product.getPrice());
                    orders.setAmount(response.getAmount());
                    this.orderRepository.save(orders);

                    product.setTotalAmount(product.getTotalAmount() - response.getAmount() + customer.getAmount());
                    product.setTotalPrice(product.getTotalPrice() + customer.getAmount() * product.getPrice() - (response.getAmount() * product.getPrice()));
                    this.productRepository.save(product);
                    customer.setAmount(response.getAmount());
                }
            }
            this.customerRepository.save(customer);
            return true;
        }
        return false;
    }
}
