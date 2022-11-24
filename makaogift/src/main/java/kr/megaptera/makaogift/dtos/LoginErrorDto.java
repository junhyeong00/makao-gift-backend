package kr.megaptera.makaogift.dtos;

public class LoginErrorDto {
    private String errorMessage;
    public LoginErrorDto(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
