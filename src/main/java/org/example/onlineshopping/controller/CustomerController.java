package org.example.onlineshopping.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.onlineshopping.response.ApiResponse;
import org.example.onlineshopping.response.CustomerResponse;
import org.example.onlineshopping.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
@Tag(name = "CUSTOMER")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CustomerResponse response,
                                    @RequestParam("productIds") List<Integer> productIds) {
        return ResponseEntity.ok(this.customerService.create(response, productIds));
    }

    @GetMapping("/get")
    public ResponseEntity<?> get(@RequestPart("id") Integer id) {
        return ResponseEntity.ok(this.customerService.get(id));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestParam("id") Integer id,
                                    @RequestBody CustomerResponse customerResponse) {
        return ResponseEntity.ok(this.customerService.update(id, customerResponse));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam("id") Integer id) {
        return ResponseEntity.ok(this.customerService.delete(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(this.customerService.getAll());
    }

    @GetMapping("/customerWithProductsById")
    public ResponseEntity<?> customerWithProductsById(@RequestParam("id") Integer id) {
        return ResponseEntity.ok(this.customerService.customerWithProductsById(id));
    }

    @GetMapping("/customerWithOrdersById")
    public ResponseEntity<?> customerWithOrdersById(@RequestParam("id") Integer id) {
        return ResponseEntity.ok(this.customerService.customerWithOrdersById(id));
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filter(@RequestParam(value = "id", required = false) Integer id,
                                    @RequestParam(value = "username", required = false) String username,
                                    @RequestParam(value = "amount", required = false) Double amount) {
        return ResponseEntity.ok(this.customerService.filter(id, username, amount));
    }
}
