package kr.megaptera.makaogift.services;

import kr.megaptera.makaogift.models.Product;
import kr.megaptera.makaogift.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
        List<Product> products = List.of(
                new Product(1L, "제조사 1", "상품 1", 500L, "상품 설명 1"),
                new Product(2L, "제조사 2", "상품 2", 2000L, "상품 설명 2"),
                new Product(3L, "제조사 3", "상품 3", 3000L, "상품 설명 3"),
                new Product(4L, "제조사 4", "상품 4", 2000L, "상품 설명 4"),
                new Product(5L, "제조사 5", "상품 5", 3000L, "상품 설명 5")
        );

        int page = 2;

        Page<Product> pageableProducts =
                new PageImpl<>(products, PageRequest.of(page - 1, 2), products.size());

        given(productRepository.findAll(any(Pageable.class)))
                .willReturn(pageableProducts);

        Page<Product> foundProducts = productService.products(page);

        assertThat(foundProducts).hasSize(products.size());

        verify(productRepository).findAll(any(Pageable.class));
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
