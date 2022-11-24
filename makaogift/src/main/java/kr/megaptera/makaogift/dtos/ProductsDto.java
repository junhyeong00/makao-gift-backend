package kr.megaptera.makaogift.dtos;

import org.springframework.data.domain.Page;

import java.util.List;

public class ProductsDto {
    private final Page<ProductDto> products;
    private int totalPageCount;

    public ProductsDto(Page<ProductDto> products, int totalPageCount) {
        this.products = products;
        this.totalPageCount = totalPageCount;
    }

    public Page<ProductDto> getProducts() {
        return products;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }
}
