package kr.megaptera.makaogift.dtos;

public class OrderErrorDto{
    private String errorMessage;
    public OrderErrorDto(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
