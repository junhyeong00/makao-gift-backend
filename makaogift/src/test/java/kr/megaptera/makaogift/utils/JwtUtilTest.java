package kr.megaptera.makaogift.utils;

import com.auth0.jwt.exceptions.JWTDecodeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JwtUtilTest {
    private JwtUtil jwtUtil;
    private final String secret = "SECRET";

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil(secret);
    }

    @Test
    void encodeAndDecode() {
        String userName = "test123";

        String token = jwtUtil.encode(userName);

        assertThat(token).contains(".");

        assertThat(jwtUtil.decode(token)).isEqualTo(userName);
    }

    @Test
    void decodeError() {
        assertThrows(JWTDecodeException.class, () -> {
            jwtUtil.decode("xxx");
        });
    }
}
