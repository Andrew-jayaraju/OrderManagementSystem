package com.orderManagementSystem.controller;

import com.orderManagementSystem.dto.request.ProductDto;
import com.orderManagementSystem.exception.ProductAlreadyExistException;
import com.orderManagementSystem.exception.ProductNotFoundException;
import com.orderManagementSystem.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@Valid @RequestBody ProductDto productDto) throws ProductAlreadyExistException {
        String message = productService.addProduct(productDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(message);
    }

    @PostMapping("/addAll")
    public ResponseEntity<String> addAllProducts(@Valid @RequestBody List<ProductDto> productDto) throws ProductAlreadyExistException {
        String message = productService.addAllProducts(productDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(message);
    }

    @GetMapping("/fetch")
    public ResponseEntity<ProductDto> fetchProduct(@RequestParam String name) throws ProductNotFoundException {
        ProductDto product = productService.fetchProduct(name);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(product);
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<List<ProductDto>> fetchAllProducts(){
        List<ProductDto> list = productService.fetchAllProducts();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(list);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateProduct(@RequestParam String name, @Valid @RequestBody ProductDto productDto) throws ProductNotFoundException {
        String message = productService.updateProduct(name, productDto);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(message);
    }


}
