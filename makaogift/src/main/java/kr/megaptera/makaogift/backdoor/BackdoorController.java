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

        if (count == 5 || count == 10) {
        jdbcTemplate.update("INSERT INTO " +
                        "product(id, maker, name, price, description, image_url)" +
                        "VALUES(?, ?, ?, ?, ?, ?)",
                1, "포맨트", "포맨트 시그니처 퍼퓸", 38000, "코튼허그",
                "https://user-images.githubusercontent.com/107493122/203739832-d4b4abc5-8a54-4d11-a8de-3b61151df68a.png");

        jdbcTemplate.update("INSERT INTO " +
                        "product(id, maker, name, price, description, image_url)" +
                        "VALUES(?, ?, ?, ?, ?, ?)",
                2, "포맨트", "포맨트 시그니처 퍼퓸", 37000, "코튼허그/코튼키스",
                "https://user-images.githubusercontent.com/107493122/203739982-10f62932-a5c4-46d8-ab24-57b6346038e5.png");

        jdbcTemplate.update("INSERT INTO " +
                        "product(id, maker, name, price, description, image_url)" +
                        "VALUES(?, ?, ?, ?, ?, ?)",
                3, "존바바토스", "아티산 EDT", 29000, "존바바토스 아티산",
                "https://user-images.githubusercontent.com/107493122/203740322-b3a79aa0-7a99-468d-a0b1-2373ec6e404c.png");

        jdbcTemplate.update("INSERT INTO " +
                        "product(id, maker, name, price, description, image_url)" +
                        "VALUES(?, ?, ?, ?, ?, ?)",
                4, "그라펜", "그파펜 타추 퍼퓸", 37000, "원우드",
                "https://user-images.githubusercontent.com/107493122/203742697-37a7e807-a6e0-4185-8222-b2c8ee9e8843.png");

        jdbcTemplate.update("INSERT INTO " +
                        "product(id, maker, name, price, description, image_url)" +
                        "VALUES(?, ?, ?, ?, ?, ?)",
                5, "포맨트", "포맨트 시그니처 퍼퓸", 46500, "오션 한정판",
                "https://user-images.githubusercontent.com/107493122/203743129-491e7d9d-38fc-4a78-b18f-0d699d262421.png");
        }

        if (count == 10) {
            jdbcTemplate.update("INSERT INTO " +
                            "product(id, maker, name, price, description, image_url)" +
                            "VALUES(?, ?, ?, ?, ?, ?)",
                    6, "아로마티카", "아로마티카 디퓨저 100ml", 23000, "메디테이팅/서렌",
                    "https://user-images.githubusercontent.com/107493122/203743251-ae051d6c-d6aa-4752-b070-805df6432807.png");

            jdbcTemplate.update("INSERT INTO " +
                            "product(id, maker, name, price, description, image_url)" +
                            "VALUES(?, ?, ?, ?, ?, ?)",
                    7, "라운드어라운드", "댕댕이 디퓨저", 18000, "귀여운 댕댕이",
                    "https://user-images.githubusercontent.com/107493122/203743506-e0a17715-1530-4c3c-a6ec-fcf28e6ee482.png");

            jdbcTemplate.update("INSERT INTO " +
                            "product(id, maker, name, price, description, image_url)" +
                            "VALUES(?, ?, ?, ?, ?, ?)",
                    8, "쿤달", "쿤달 퍼퓸 디퓨저", 8200, "4종 택 1",
                    "https://user-images.githubusercontent.com/107493122/203743839-acd95505-6715-434c-a561-a3c73101739e.png");

            jdbcTemplate.update("INSERT INTO " +
                            "product(id, maker, name, price, description, image_url)" +
                            "VALUES(?, ?, ?, ?, ?, ?)",
                    9, "더블유드레스룸", "라이프 퍼퓸백", 8000, "러브 상탈/휘그 우디/오드 워터",
                    "https://user-images.githubusercontent.com/107493122/203744116-d32334d3-8fbd-40fb-81fc-a3776046da2b.png");

            jdbcTemplate.update("INSERT INTO " +
                            "product(id, maker, name, price, description, image_url)" +
                            "VALUES(?, ?, ?, ?, ?, ?)",
                    10, "라운드어라운드", "라운드어라운드 선인장 디퓨저", 12000, "뾰족뾰족",
                    "https://user-images.githubusercontent.com/107493122/203744468-582a1d30-968a-40ca-888c-ff9fc88240a0.png");
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

        jdbcTemplate.update("INSERT INTO " +
                        "person(id, user_Name, name, encoded_Password, amount)" +
                        "VALUES(2, ?, ?, ?, ?)",
                "test0000", "산토끼", passwordEncoder.encode("Password1234!"), 0);

        return "OK";
    }

    @GetMapping("setup-orders")
    public String setupOrders() {
        LocalDateTime now = LocalDateTime.now();

        jdbcTemplate.execute("DELETE FROM transaction");

        jdbcTemplate.update("INSERT INTO " +
                        "transaction(id, sender, maker, name, purchase_Count, purchase_Price," +
                        "   receiver, address, message_To_Send, created_At, image_url)" +
                        "VALUES(1, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                "김토끼", "라운드어라운드", "라운드어라운드 선인장 디퓨저", 3, 36_000, "산토끼", "산", "안녕", now,
                "https://user-images.githubusercontent.com/107493122/203744468-582a1d30-968a-40ca-888c-ff9fc88240a0.png");

        jdbcTemplate.update("INSERT INTO " +
                        "transaction(id, sender, maker, name, purchase_Count, purchase_Price," +
                        "   receiver, address, message_To_Send, created_At, image_url)" +
                        "VALUES(2, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                "김토끼", "더블유드레스룸", "라이프 퍼퓸백", 4, 20_000, "산토끼", "산", "잘지내니", now,
                "https://user-images.githubusercontent.com/107493122/203744116-d32334d3-8fbd-40fb-81fc-a3776046da2b.png");

        jdbcTemplate.update("INSERT INTO " +
                        "transaction(id, sender, maker, name, purchase_Count, purchase_Price," +
                        "   receiver, address, message_To_Send, created_At, image_url)" +
                        "VALUES(3, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                "산토끼", "라운드어라운드", "댕댕이 디퓨저", 2, 40_000, "김토끼", "서울", "잘지내지", now,
                "https://user-images.githubusercontent.com/107493122/203743506-e0a17715-1530-4c3c-a6ec-fcf28e6ee482.png");
        return "OK";
    }
}
