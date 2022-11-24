package kr.megaptera.makaogift.dtos;

public class RegisterResultDto {
    private final String name;

    public RegisterResultDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
