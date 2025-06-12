package com.orderManagementSystem.service;

import com.orderManagementSystem.doa.ProductRepository;
import com.orderManagementSystem.dto.mapper.ProductMapper;
import com.orderManagementSystem.dto.request.ProductDto;
import com.orderManagementSystem.dto.response.ProductResponse;
import com.orderManagementSystem.entity.Product;
import com.orderManagementSystem.exception.ProductAlreadyExistException;
import com.orderManagementSystem.exception.ProductNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public String addProduct(ProductDto productDto) throws ProductAlreadyExistException {
        Product product = productRepository.findByNameContaining(productDto.getName());
        if(product != null){
            throw new ProductAlreadyExistException("Product already exist with the given name");
        }
        productRepository.save(ProductMapper.productDtoMapper(productDto));
        return "Product added successfully";
    }

    public String addAllProducts(List<ProductDto> productDto) throws ProductAlreadyExistException {
        for(ProductDto prod : productDto){
            Product product = productRepository.findByNameContaining(prod.getName());
            if(product != null){
                throw new ProductAlreadyExistException("Product already exist with the given name");
            }
        }
        List<Product> products = new ArrayList<>();
        for(ProductDto p : productDto){
            products.add(ProductMapper.productDtoMapper(p));
        }
        productRepository.saveAll(products);
        return "Product added successfully";
    }


    public ProductDto fetchProduct(String name) throws ProductNotFoundException {
        Product product = productRepository.findByNameContaining(name);
        if(product == null){
            throw new ProductNotFoundException("Product does not exist");
        }
        return ProductMapper.productMapper(product);
    }

    public List<ProductDto> fetchAllProducts() {
        List<ProductDto> list = new ArrayList<>();
        for(Product prod : productRepository.findAll()){
            list.add(ProductMapper.productMapper(prod));
        }
        return list;
    }

    public String updateProduct(String name, ProductDto productDto) throws ProductNotFoundException {
        Product product = productRepository.findByNameContaining(name);
        if(product == null){
            throw new ProductNotFoundException("Product does not exist");
        }
        product.setName(productDto.getName());
        product.setStock(productDto.getStock());
        product.setPrice(productDto.getPrice());
        productRepository.save(product);
        return "Product updated successfully";
    }

}
