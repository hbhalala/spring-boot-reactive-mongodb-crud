package com.example.mongodbcrud.repository;

import com.example.mongodbcrud.entity.Product;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
    public Flux<Product> findByPriceBetween(Range<Double> priceRange);
}
