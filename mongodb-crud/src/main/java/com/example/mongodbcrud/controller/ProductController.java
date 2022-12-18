package com.example.mongodbcrud.controller;

import com.example.mongodbcrud.dto.ProductDto;
import com.example.mongodbcrud.service.ProductService;
import com.example.mongodbcrud.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Flux<ProductDto>> getAllProducts() {
        return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<ProductDto>> getProduct(@PathVariable String id) {
        return new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
    }

    @GetMapping("/product-range")
    public ResponseEntity<Flux<ProductDto>> getProductBetweenRange(@RequestParam("min") double min, @RequestParam("max") double max) {
        return new ResponseEntity<>(productService.getProductInRange(min, max), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Mono<ProductDto>> saveProduct(@RequestBody Mono<ProductDto> productDtoMono) {
        return new ResponseEntity<>(productService.saveProduct(productDtoMono), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Mono<ProductDto>> saveProduct(@RequestBody Mono<ProductDto> productDtoMono, @PathVariable String id) {
        return new ResponseEntity<>(productService.updateProduct(productDtoMono, id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Mono<Void>> deleteProduct(@PathVariable String id) {
        return new ResponseEntity<>(productService.deleteProduct(id), HttpStatus.OK);
    }

}
