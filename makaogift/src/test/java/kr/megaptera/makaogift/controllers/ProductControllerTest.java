package kr.megaptera.makaogift.controllers;

import kr.megaptera.makaogift.models.Product;
import kr.megaptera.makaogift.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void products() throws Exception {
        List<Product> products = List.of(
                new Product(1L, "제조사 1", "상품 1", 500L, "상품 설명 1"),
                new Product(2L, "제조사 2", "상품 2", 2000L, "상품 설명 2"),
                new Product(2L, "제조사 3", "상품 3", 3000L, "상품 설명 3")
        );

        given(productService.products()).willReturn(products);

        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"products\":[")
                ))
                .andExpect(content().string(
                        containsString("2000")
                ));

        verify(productService).products();
    }

    @Test
    void product() throws Exception {
        Product product = new Product(
                1L,
                "제조사",
                "test",
                10000L,
                "좋다"
        );

        given(productService.product(any()))
                .willReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("test")
                ));
    }
}
