package kr.megaptera.makaogift.models;

import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PERSON")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String userName;

    private String encodedPassword;

    private String name;

    private Long amount;

    public User() {
    }

    public User(Long id, String userName, String name, Long amount) {
        this.id = id;
        this.userName = userName;
        this.name = name;
        this.amount = amount;
    }

    public String userName() {
        return userName;
    }

    public String name() {
        return name;
    }

    public Long amount() {
        return amount;
    }

    public static User fake(String userName) {
        return new User();
    }

    public boolean authenticate(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, this.encodedPassword);
    }

    public void changePassword(String password, PasswordEncoder passwordEncoder) {
        this.encodedPassword = passwordEncoder.encode(password);
    }

    public void reduceAmount(Long purchasePrice) {
        this.amount -= purchasePrice;
    }
}
