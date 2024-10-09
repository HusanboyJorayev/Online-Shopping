package org.example.onlineshopping.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.onlineshopping.dto.CustomerWithProductResponse;
import org.example.onlineshopping.dto.OrderDto;
import org.example.onlineshopping.entity.Customer;
import org.example.onlineshopping.entity.Order;
import org.example.onlineshopping.entity.Product;
import org.example.onlineshopping.filter.CustomerFilter;
import org.example.onlineshopping.mapper.CustomerMapper;
import org.example.onlineshopping.mapper.CustomerMapperImpls;
import org.example.onlineshopping.mapper.OrderMapper;
import org.example.onlineshopping.repository.CustomerRepository;
import org.example.onlineshopping.repository.OrderRepository;
import org.example.onlineshopping.repository.ProductRepository;
import org.example.onlineshopping.request.CustomerRequest;
import org.example.onlineshopping.response.ApiResponse;
import org.example.onlineshopping.response.CustomerResponse;
import org.example.onlineshopping.service.CustomerService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerMapperImpls customerMapperImpls;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final CustomerMapper customerMapper;
    private final OrderRepository orderRepository;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final OrderMapper orderMapper;

    @Override
    public ApiResponse<CustomerRequest> create(CustomerResponse response, List<Integer> productIds) {
        List<Product> products = this.productRepository.selectProductsById(productIds);
        if (products == null) {
            Customer entity = this.customerMapper.toEntity(response);
            this.customerRepository.save(entity);
            return ApiResponse.<CustomerRequest>builder()
                    .status(HttpStatus.CREATED)
                    .message("Customer created successfully")
                    .build();
        }
        Customer customer = this.customerMapper.toEntity(response);
        this.customerRepository.save(customer);
        for (Product product : products) {
            Order order = new Order();


            order.setCustomerId(customer.getId());
            order.setProductId(product.getId());
            order.setAmount(response.getAmount());
            order.setTotalPrice(product.getPrice() * response.getAmount());
            this.orderRepository.save(order);


            product.setTotalPrice(product.getTotalPrice() - (product.getPrice() * response.getAmount()));
            product.setTotalAmount(product.getTotalAmount() - response.getAmount());
            this.productRepository.save(product);
        }
        return ApiResponse.<CustomerRequest>builder()
                .status(HttpStatus.OK)
                .message("Customer created with product")
                .build();
    }


    @Override
    public ApiResponse<CustomerRequest> get(Integer id) {
        Optional<Customer> optional = this.customerRepository.findById(id);
        if (optional.isPresent()) {
            Customer customer = optional.get();
            CustomerRequest request = this.customerMapperImpls.toDto(customer);
            return ApiResponse.<CustomerRequest>builder()
                    .message("SUCCESS")
                    .status(HttpStatus.OK)
                    .data(request)
                    .build();
        }
        return ApiResponse.<CustomerRequest>builder()
                .message("CUSTOMER IS NOT FOUND")
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

    @Override
    public ApiResponse<String> update(Integer id, CustomerResponse response) {
        if (this.customerMapperImpls.updated(id, response)) {
            return ApiResponse.<String>builder()
                    .status(HttpStatus.OK)
                    .message("Updated")
                    .build();
        }
        return ApiResponse.<String>builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Customer NOT FOUND")
                .build();
    }

    @Override
    public ApiResponse<String> delete(Integer id) {
        Optional<Customer> optional = this.customerRepository.findById(id);
        if (optional.isPresent()) {
            Customer customer = optional.get();
            this.orderRepository.deleteById(customer.getOrders().getId());
            this.customerRepository.deleteById(id);
            return ApiResponse.<String>builder()
                    .status(HttpStatus.OK)
                    .message("DELETED")
                    .build();
        }
        return ApiResponse.<String>builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Customer NOT FOUND")
                .build();
    }

    @Override
    public ApiResponse<List<CustomerRequest>> getAll() {
        List<Customer> list = this.customerRepository.findAll();
        if (!list.isEmpty()) {
            List<CustomerRequest> customerRequest = this.customerMapper.toCustomerRequest(list);
            return ApiResponse.<List<CustomerRequest>>builder()
                    .status(HttpStatus.OK)
                    .message("OK")
                    .data(customerRequest)
                    .build();
        }
        return ApiResponse.<List<CustomerRequest>>builder()
                .status(HttpStatus.OK)
                .message("List is empty")
                .build();
    }

    @Override
    public ApiResponse<?> customerWithProductsById(Integer id) {

        String sql = "select c.id as Id,\n" +
                "       c.amount as Amount,\n" +
                "       c.username as Username,\n" +
                "       p.price as Price,\n" +
                "       p.name as Name,\n" +
                "       p.discount as Discount,\n" +
                "       p.discount_price as DiscountPrice,\n" +
                "       p.id as ProductId,\n" +
                "       o.total_price as ToatalPrice,\n" +
                "       o.id as OrderId\n" +
                "from customer as c\n" +
                "left join orders as o on o.customer_id=c.id\n" +
                "left join product as p on o.product_id = p.id ";
        MapSqlParameterSource source = new MapSqlParameterSource();
        sql += " where c.id=:id";
        source.addValue("id", id);

        List<CustomerWithProductResponse> query = jdbcTemplate.query(sql, source, r -> {
            List<CustomerWithProductResponse> responses = new ArrayList<>();
            while (r.next()) {
                Integer customerId = r.getInt("Id") != 0 ? r.getInt("Id") : null;
                Double amount = r.getDouble("Amount") != 0 ? r.getDouble("Amount") : null;
                String username = r.getString("Username");
                Double price = r.getDouble("Price") != 0 ? r.getDouble("Price") : null;
                String name = r.getString("Name");
                Double discount = r.getDouble("Discount") != 0 ? r.getDouble("Discount") : null;
                Double discountPrice = r.getDouble("DiscountPrice") != 0 ? r.getDouble("DiscountPrice") : null;
                Integer productId = r.getInt("ProductId") != 0 ? r.getInt("ProductId") : null;
                Double totalPrice = r.getDouble("ToatalPrice") != 0 ? r.getDouble("ToatalPrice") : null;
                Integer orderId = r.getInt("OrderId") != 0 ? r.getInt("OrderId") : null;

                responses.add(
                        new CustomerWithProductResponse(
                                customerId,
                                amount,
                                username,
                                price,
                                name,
                                discount,
                                discountPrice,
                                productId,
                                totalPrice
                        )
                );
            }
            return responses;
        });
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("Success")
                .data(query)
                .build();
    }

    @Override
    public ApiResponse<?> customerWithOrdersById(Integer id) {
        List<Order> orders = this.orderRepository.findByCustomerId(id);
        List<OrderDto> list = new ArrayList<>();
        if (!orders.isEmpty()) {
            List<OrderDto> orderDtoList = this.orderMapper.toDtoList(orders);
            list.addAll(orderDtoList);
        }
        Optional<Customer> optional = this.customerRepository.findById(id);
        CustomerWithProductResponse.ShortResponse shortResponse = null;
        if (optional.isPresent()) {
            Customer customer = optional.get();
            shortResponse = new CustomerWithProductResponse.ShortResponse(
                    customer.getId(),
                    customer.getUsername(),
                    list
            );
        }
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("OK")
                .data(shortResponse)
                .build();
    }

    @Override
    public ApiResponse<?> filter(Integer id, String username, Double amount) {
        Specification<Customer> specification = new CustomerFilter(id, username, amount);
        List<Customer> list = this.customerRepository.findAll(specification);
        if (!list.isEmpty()) {
            List<CustomerRequest> customerRequest = this.customerMapper.toCustomerRequest(list);
            return ApiResponse.builder()
                    .status(HttpStatus.OK)
                    .message("OK")
                    .data(customerRequest)
                    .build();
        }
        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("Ok")
                .data(new ArrayList<>())
                .build();
    }
}
