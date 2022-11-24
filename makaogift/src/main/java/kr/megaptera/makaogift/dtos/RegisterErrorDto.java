package kr.megaptera.makaogift.dtos;

import java.util.Map;

public class RegisterErrorDto {
    private final Map<Integer, String> errorCodesAndMessages;

    public RegisterErrorDto(Map<Integer, String> errorCodesAndMessages) {
        this.errorCodesAndMessages = errorCodesAndMessages;
    }

    public Map<Integer, String> getErrorCodesAndMessages() {
        return errorCodesAndMessages;
    }
}
