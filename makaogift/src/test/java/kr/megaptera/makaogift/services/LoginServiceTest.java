package kr.megaptera.makaogift.services;

import kr.megaptera.makaogift.exceptions.LoginFailed;
import kr.megaptera.makaogift.models.User;
import kr.megaptera.makaogift.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class LoginServiceTest {
    private LoginService loginService;

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);

        passwordEncoder = new Argon2PasswordEncoder();

        loginService = new LoginService(userRepository, passwordEncoder);
    }

    @Test
    void loginSuccess() {
        User user = User.fake("test123");
        user.changePassword("Password1234!", passwordEncoder);

        given(userRepository.findByUserName("test123"))
                .willReturn(Optional.of(user));

        User found = loginService.login("test123", "Password1234!");

        assertThat(found).isNotNull();
    }

    @Test
    void loginWithIncorrectUserName() {
        assertThrows(LoginFailed.class, () -> {
            loginService.login("xxx", "Password1234!");
        });
    }

    @Test
    void loginWithIncorrectPassword() {
        assertThrows(LoginFailed.class, () -> {
            loginService.login("test123", "xxx");
        });
    }
}
