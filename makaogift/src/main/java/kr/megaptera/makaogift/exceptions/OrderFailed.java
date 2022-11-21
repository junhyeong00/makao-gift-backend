package kr.megaptera.makaogift.exceptions;

public class OrderFailed extends RuntimeException {
    private String errorMessage;

    public OrderFailed(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
