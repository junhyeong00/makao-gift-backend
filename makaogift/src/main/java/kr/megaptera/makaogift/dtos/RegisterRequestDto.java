package kr.megaptera.makaogift.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class RegisterRequestDto {
    @NotBlank(message = "이름을 입력해주세요")
    @Pattern(regexp = "^[가-힣]{3,7}$",
            message = "이름을 다시 확인해주세요")
    private final String name;

    @NotBlank(message = "아이디를 입력해주세요")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)[a-z\\d]{4,16}$",
            message = "아이디를 다시 확인해주세요")
    private final String userName;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d(?=.*@$!%*#?&)]{8,}$",
            message = "비밀번호를 다시 확인해주세요")
    private final String password;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private final String confirmPassword;

    public RegisterRequestDto(String name, String userName,
                                  String password, String confirmPassword) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}
