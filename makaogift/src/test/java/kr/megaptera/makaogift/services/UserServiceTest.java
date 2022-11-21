package kr.megaptera.makaogift.services;

import kr.megaptera.makaogift.models.User;
import kr.megaptera.makaogift.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserServiceTest {
    UserService userService;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    User user;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);

        userService = new UserService(userRepository);

        passwordEncoder = new Argon2PasswordEncoder();

        Long userId = 1L;
        String userName = "test123";
        String password = "Password1234!";
        String name = "김토끼";
        Long amount = 50000L;

        user = new User(userId, userName, name, amount);
    }

    @Test
    void amount() {
        given(userRepository.findByUserName(user.userName()))
                .willReturn(Optional.of(user));

        Long amount = userService.amount(user.userName());

        assertThat(amount).isEqualTo(50000L);

        verify(userRepository).findByUserName(user.userName());
    }
}
