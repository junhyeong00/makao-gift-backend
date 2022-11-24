package kr.megaptera.makaogift.controllers;

import kr.megaptera.makaogift.dtos.RegisterErrorDto;
import kr.megaptera.makaogift.dtos.RegisterRequestDto;
import kr.megaptera.makaogift.dtos.RegisterResultDto;
import kr.megaptera.makaogift.dtos.UserAmountDto;
import kr.megaptera.makaogift.exceptions.RegisterFailed;
import kr.megaptera.makaogift.models.User;
import kr.megaptera.makaogift.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("user")
public class UserController {
    private static final Integer BLANK_NAME = 1000;
    private static final Integer BLANK_IDENTIFICATION = 1001;
    private static final Integer BLANK_PASSWORD = 1002;
    private static final Integer BLANK_CONFIRM_PASSWORD = 1003;
    private static final Integer INVALID_NAME = 1004;
    private static final Integer INVALID_IDENTIFICATION = 1005;
    private static final Integer INVALID_PASSWORD = 1006;
    private static final Integer ALREADY_EXISTING_IDENTIFICATION = 1007;
    private static final Integer NOT_MATCHING_PASSWORD = 1008;

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("amount")
    public UserAmountDto amount(
            @RequestAttribute("userName") String userName
    ) {
        Long amount = userService.amount(userName);

        return new UserAmountDto(amount);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResultDto register(
            @Validated @RequestBody RegisterRequestDto registerRequestDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .toList();
            throw new RegisterFailed(errorMessages);
        }

        User user = userService.createUser(
                registerRequestDto.getName(),
                registerRequestDto.getUserName(),
                registerRequestDto.getPassword(),
                registerRequestDto.getConfirmPassword()
        );

        return new RegisterResultDto(user.name());
    }

    @ExceptionHandler(RegisterFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RegisterErrorDto registerFailed(RegisterFailed e) {
        Map<Integer, String> errorCodesAndMessages = e.getErrorMessages().stream()
                .collect(Collectors.toMap(this::mapToErrorCode,
                        errorMessage -> errorMessage));
        return new RegisterErrorDto(errorCodesAndMessages);
    }

    public Integer mapToErrorCode(String errorMessage) {
        return switch (errorMessage) {
            case "이름을 입력해주세요" -> BLANK_NAME;
            case "아이디를 입력해주세요" -> BLANK_IDENTIFICATION;
            case "비밀번호를 입력해주세요" -> BLANK_PASSWORD;
            case "비밀번호 확인을 입력해주세요" -> BLANK_CONFIRM_PASSWORD;
            case "이름을 다시 확인해주세요" -> INVALID_NAME;
            case "아이디를 다시 확인해주세요" -> INVALID_IDENTIFICATION;
            case "비밀번호를 다시 확인해주세요" -> INVALID_PASSWORD;
            case "해당 아이디는 사용할 수 없습니다" -> ALREADY_EXISTING_IDENTIFICATION;
            case "비밀번호가 일치하지 않습니다" -> NOT_MATCHING_PASSWORD;
            default -> 9999;
        };
    }
}
