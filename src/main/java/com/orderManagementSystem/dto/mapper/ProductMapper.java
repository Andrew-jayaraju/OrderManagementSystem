package com.orderManagementSystem.dto.mapper;

import com.orderManagementSystem.dto.request.ProductDto;
import com.orderManagementSystem.dto.response.ProductResponse;
import com.orderManagementSystem.entity.Product;

public class ProductMapper {

    public static Product productDtoMapper(ProductDto productDto){
        return Product.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .stock(productDto.getStock())
                .build();
    }

    public static ProductDto productMapper(Product product){
        return ProductDto.builder()
                .name(product.getName())
                .stock(product.getStock())
                .price(product.getPrice())
                .build();
    }

    public static ProductResponse productResponseMapper(Product product){
        return ProductResponse.builder()
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
}
