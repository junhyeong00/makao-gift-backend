package kr.megaptera.makaogift.backdoor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("backdoor")
public class BackdoorController {
    private final JdbcTemplate jdbcTemplate;

    private final PasswordEncoder passwordEncoder;

    public BackdoorController(JdbcTemplate jdbcTemplate,
                              PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("setup-products")
    public String setupProducts(
            @RequestParam Long count
    ) {
        jdbcTemplate.execute("DELETE FROM product");

        for (long i = 1; i <= count; i += 1) {
            jdbcTemplate.update("INSERT INTO " +
                            "product(id, maker, name, price, description)" +
                            "VALUES(?, ?, ?, ?, ?)",
                    i, "제조사 " + i, "상품 " + i, i * 100, "설명 " + i);
        }

        return "OK";
    }

    @GetMapping("setup-user")
    public String setupUser() {
        jdbcTemplate.execute("DELETE FROM person");

        jdbcTemplate.update("INSERT INTO " +
                        "person(id, user_Name, name, encoded_Password, amount)" +
                        "VALUES(1, ?, ?, ?, ?)",
                "test123", "김토끼", passwordEncoder.encode("Password1234!"), 50_000);

        return "OK";
    }
}
