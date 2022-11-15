package kr.megaptera.makaogift.backdoor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("backdoor")
public class BackdoorController {
    private final JdbcTemplate jdbcTemplate;

    public BackdoorController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
}
