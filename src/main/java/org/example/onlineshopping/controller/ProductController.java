package org.example.onlineshopping.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.onlineshopping.request.ProductRequest;
import org.example.onlineshopping.response.ApiResponse;
import org.example.onlineshopping.response.ProductResponse;
import org.example.onlineshopping.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
@Tag(name = "PRODUCT")
public class ProductController {
    private final ProductService productService;


    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ProductResponse response) {
        ApiResponse<ProductRequest> apiResponse = this.productService.create(response);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/get")
    private ResponseEntity<?> get(@RequestParam("id") Integer id) {
        return ResponseEntity.ok(this.productService.get(id));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody ProductResponse response,
                                    @RequestParam("id") Integer id) {
        return ResponseEntity.ok(this.productService.update(id, response));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam("id") Integer id) {
        return ResponseEntity.ok(this.productService.delete(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(this.productService.getAll());
    }

    @PostMapping("/discount")
    public ResponseEntity<?> discount(@RequestParam("discount") Double discount,
                                      @RequestParam("id") Integer id) {
        return ResponseEntity.ok(this.productService.discount(discount, id));
    }
}
