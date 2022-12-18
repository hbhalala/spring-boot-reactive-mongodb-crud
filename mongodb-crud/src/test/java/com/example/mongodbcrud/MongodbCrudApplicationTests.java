package com.example.mongodbcrud;

import com.example.mongodbcrud.controller.ProductController;
import com.example.mongodbcrud.dto.ProductDto;
import com.example.mongodbcrud.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@WebFluxTest(ProductController.class)
public class MongodbCrudApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private ProductService productService;

	@Test
	void contextLoads() {
	}

	@Test
	public void addProductTest() {
		Mono<ProductDto> productDtoMono = Mono.just(new ProductDto("102", "mobile", 10, 1000));
		when(productService.saveProduct(productDtoMono)).thenReturn(productDtoMono);

		webTestClient.post()
				.uri("/products")
				.body(Mono.just(productDtoMono), ProductDto.class)
				.exchange()
				.expectStatus()
				.isOk();
	}

	@Test
	public void getProductsTest() {
		Flux<ProductDto> productDtoFlux = Flux.just(new ProductDto("102", "mobile", 10, 1000),
				new ProductDto("103", "mobile", 10, 1000));
		when(productService.getProducts()).thenReturn(productDtoFlux);

		Flux<ProductDto> responseBody = webTestClient.get()
				.uri("/products")
				.exchange()
				.expectStatus()
				.isOk()
				.returnResult(ProductDto.class)
				.getResponseBody();

		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNext(new ProductDto("102", "mobile", 10, 1000))
				.expectNext(new ProductDto("103", "mobile", 10, 1000))
				.verifyComplete();
	}

	@Test
	public void getProductTest() {
		Mono<ProductDto> productDtoMono = Mono.just(new ProductDto("104", "book", 10, 200));
		when(productService.getProduct(any())).thenReturn(productDtoMono);

		Flux<ProductDto> responseBody = webTestClient.get()
				.uri("/products/104")
				.exchange()
				.expectStatus().isOk()
				.returnResult(ProductDto.class)
				.getResponseBody();

		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNextMatches(p->p.getName().equals("book"))
				.verifyComplete();

	}

	@Test
	public void updateProductTest() {
		Mono<ProductDto> productDtoMono = Mono.just(new ProductDto("104", "pen", 100, 99));
		when(productService.updateProduct(productDtoMono, "104")).thenReturn(productDtoMono);

		webTestClient.put()
				.uri("/products/update/104")
				.body(productDtoMono, ProductDto.class)
				.exchange()
				.expectStatus().isOk();
	}

	@Test
	public void deleteProductTest() {
		given(productService.deleteProduct(any())).willReturn(Mono.empty());

		webTestClient.delete()
				.uri("/products/delete/102")
				.exchange()
				.expectStatus().isOk();
	}

}
