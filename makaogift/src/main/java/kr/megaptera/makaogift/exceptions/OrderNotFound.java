package kr.megaptera.makaogift.exceptions;

public class OrderNotFound extends RuntimeException {
    public OrderNotFound() {
        super("주문 내역을 찾을 수 없습니다");
    }
}
