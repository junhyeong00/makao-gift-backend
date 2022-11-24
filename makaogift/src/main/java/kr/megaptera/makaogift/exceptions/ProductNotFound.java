package kr.megaptera.makaogift.exceptions;

public class ProductNotFound extends RuntimeException{
    public ProductNotFound() {
        super("없는 상품입니다");
    }
}
