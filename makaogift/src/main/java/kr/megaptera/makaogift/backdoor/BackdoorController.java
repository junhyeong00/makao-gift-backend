package kr.megaptera.makaogift.backdoor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

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

    @GetMapping("setup-orders")
    public String setupOrders() {
        LocalDateTime now = LocalDateTime.now();

        jdbcTemplate.execute("DELETE FROM transaction");

        jdbcTemplate.update("INSERT INTO " +
                        "transaction(id, sender, maker, name, purchase_Count, purchase_Price," +
                        "   receiver, address, message_To_Send, created_At)" +
                        "VALUES(1, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                "김토끼", "제조사 1", "상품 1", 3, 15_000, "산토끼", "산", "안녕", now);

        jdbcTemplate.update("INSERT INTO " +
                        "transaction(id, sender, maker, name, purchase_Count, purchase_Price," +
                        "   receiver, address, message_To_Send, created_At)" +
                        "VALUES(2, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                "김토끼", "제조사 2", "상품 2", 4, 20_000, "산토끼", "산", "잘지내니", now);

        jdbcTemplate.update("INSERT INTO " +
                        "transaction(id, sender, maker, name, purchase_Count, purchase_Price," +
                        "   receiver, address, message_To_Send, created_At)" +
                        "VALUES(3, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                "산토끼", "제조사 3", "상품 3", 2, 40_000, "김토끼", "서울", "잘지내지", now);
        return "OK";
    }
}
