package kr.megaptera.makaogift.controllers;

import kr.megaptera.makaogift.dtos.LoginErrorDto;
import kr.megaptera.makaogift.dtos.LoginRequestDto;
import kr.megaptera.makaogift.dtos.LoginResultDto;
import kr.megaptera.makaogift.exceptions.LoginFailed;
import kr.megaptera.makaogift.models.User;
import kr.megaptera.makaogift.services.LoginService;
import kr.megaptera.makaogift.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("session")
public class SessionController {
    private final LoginService loginService;
    private final JwtUtil jwtUtil;

    public SessionController(LoginService loginService, JwtUtil jwtUtil) {
        this.loginService = loginService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResultDto login(
            @Validated @RequestBody LoginRequestDto loginRequestDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();

            throw new LoginFailed(errorMessage);
        }

        String userName = loginRequestDto.getUserName();
        String password = loginRequestDto.getPassword();

        User user = loginService.login(userName, password);

        String accessToken = jwtUtil.encode(userName);

        return new LoginResultDto(accessToken, user.name(), user.amount());
    }

    @ExceptionHandler(LoginFailed.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public LoginErrorDto loginFailed(LoginFailed e) {
        return new LoginErrorDto(e.getMessage());
    }
}
