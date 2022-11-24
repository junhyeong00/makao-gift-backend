package kr.megaptera.makaogift.dtos;

import org.springframework.data.domain.Page;

public class OrdersDto {
    private Page<OrderDto> orders;
    private int totalPageCount;

    public OrdersDto(Page<OrderDto> orders, int totalPageCount) {
        this.orders = orders;
        this.totalPageCount = totalPageCount;
    }

    public Page<OrderDto> getOrders() {
        return orders;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }
}
