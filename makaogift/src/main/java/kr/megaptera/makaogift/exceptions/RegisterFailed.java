package kr.megaptera.makaogift.exceptions;

import java.util.List;

public class RegisterFailed extends RuntimeException {
    private final List<String> errorMessages;

    public RegisterFailed(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public RegisterFailed(String errorMessage) {
        errorMessages = List.of(errorMessage);
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }
}
