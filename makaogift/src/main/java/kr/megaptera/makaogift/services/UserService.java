package kr.megaptera.makaogift.services;

import kr.megaptera.makaogift.exceptions.RegisterFailed;
import kr.megaptera.makaogift.exceptions.UserNotFound;
import kr.megaptera.makaogift.models.User;
import kr.megaptera.makaogift.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Long amount(String userName) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserNotFound());
        return user.amount();
    }

    public User createUser(String name, String userName, String password, String confirmPassword) {
        if (userRepository.findByUserName(userName).isPresent()) {
            throw new RegisterFailed("해당 아이디는 사용할 수 없습니다");
        }

        if (!password.equals(confirmPassword)) {
            throw new RegisterFailed("비밀번호가 일치하지 않습니다");
        }

        Long amount = 50000L;
        User user = new User(userName, name, amount);
        user.changePassword(password, passwordEncoder);

        return userRepository.save(user);
    }
}
