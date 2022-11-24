package kr.megaptera.makaogift.services;

import kr.megaptera.makaogift.exceptions.RegisterFailed;
import kr.megaptera.makaogift.models.User;
import kr.megaptera.makaogift.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserServiceTest {
    UserService userService;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    User user;
    Long userId;
    String userName;
    String password;
    String name;
    Long amount;
    User savedUser;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);

        passwordEncoder = new Argon2PasswordEncoder();

        userService = new UserService(userRepository, passwordEncoder);


        userId = 1L;
        userName = "test123";
        password = "Password1234!";
        name = "김토끼";
        amount = 50000L;

        user = new User(userId, userName, name, amount);
        user.changePassword(password, passwordEncoder);

//        savedUser = new User(2L, userName, name, amount);
//        savedUser.changePassword(password, passwordEncoder);
    }

    @Test
    void amount() {
        given(userRepository.findByUserName(user.userName()))
                .willReturn(Optional.of(user));

        Long amount = userService.amount(user.userName());

        assertThat(amount).isEqualTo(50000L);

        verify(userRepository).findByUserName(user.userName());
    }

    @Test
    void createUser() {
        given(userRepository.save(user))
                .willReturn(user);

        User createdUser = userService.createUser(name, userName, password, password);

        assertThat(createdUser).isNotNull();

        verify(userRepository).save(user);
    }

    @Test
    void createUserWithAlreadyExistingUserName() {
        given(userRepository.findByUserName(userName))
                .willReturn(Optional.of(user));

        assertThrows(RegisterFailed.class, () -> {
            userService.createUser(name, userName, password, password);
        });

        verify(userRepository).findByUserName(userName);
    }

    @Test
    void createAccountWithNotMatchingPasswords() {
        String notMatchingPassword = "123123";
        assertThrows(RegisterFailed.class, () -> {
            userService.createUser(
                    name, userName, password, notMatchingPassword);
        });
    }
}
