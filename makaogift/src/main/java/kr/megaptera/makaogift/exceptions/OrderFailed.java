package kr.megaptera.makaogift.exceptions;

import java.util.List;

public class OrderFailed extends RuntimeException {
    private final List<String> errorMessages;

    public OrderFailed(String errorMessage) {
        errorMessages = List.of(errorMessage);
    }

    public OrderFailed(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }
}
