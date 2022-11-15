package kr.megaptera.makaogift.services;

import kr.megaptera.makaogift.models.Product;
import kr.megaptera.makaogift.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ProductServiceTest {
    ProductService productService;
    ProductRepository productRepository;

    @BeforeEach
    void setup() {
        productRepository = mock(ProductRepository.class);

        productService = new ProductService(productRepository);
    }

    @Test
    void products() {
        Product product = mock(Product.class);

        given(productRepository.findAll())
                .willReturn(List.of(product));

        List<Product> products = productService.products();

        assertThat(products).hasSize(1);
    }

    @Test
    void product() {
        Product product = new Product(
                1L,
                "제조사",
                "test",
                10000L,
                "좋다"
        );

        given(productRepository.findById(any()))
                .willReturn(Optional.of(product));

        Product foundProduct = productService.product(1L);

        assertThat(foundProduct).isNotNull();
        verify(productRepository).findById(any(Long.class));
    }
}
