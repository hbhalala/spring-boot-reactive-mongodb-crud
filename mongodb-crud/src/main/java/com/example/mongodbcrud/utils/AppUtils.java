package com.example.mongodbcrud.utils;

import com.example.mongodbcrud.dto.ProductDto;
import com.example.mongodbcrud.entity.Product;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;

public class AppUtils {
    public static ProductDto entityToDto(Product product) {
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto);
        return productDto;
    }

    public static Product dtoToEntity(ProductDto productDto) {
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        return product;
    }
}