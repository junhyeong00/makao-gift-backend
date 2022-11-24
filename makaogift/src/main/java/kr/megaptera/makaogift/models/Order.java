package kr.megaptera.makaogift.models;

import kr.megaptera.makaogift.dtos.OrderDto;
import kr.megaptera.makaogift.dtos.OrderResultDto;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "TRANSACTION")
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    private String sender;

    private String maker;

    private String name;

    private Integer purchaseCount;

    private Long purchasePrice;

    private String receiver;

    private String address;

    private String messageToSend;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private String imageUrl;

    public Order() {
    }

    public Order(Long id, String sender,
                 String maker, String name,
                 Integer purchaseCount, Long purchasePrice,
                 String receiver, String address,
                 String messageToSend) {
        this.id = id;
        this.sender = sender;
        this.maker = maker;
        this.name = name;
        this.purchaseCount = purchaseCount;
        this.purchasePrice = purchasePrice;
        this.receiver = receiver;
        this.address = address;
        this.messageToSend = messageToSend;
    }

    public Order(String sender, String maker,
                 String name, Integer purchaseCount,
                 Long purchasePrice, String receiver,
                 String address, String messageToSend,
                 String imageUrl) {
        this.sender = sender;
        this.maker = maker;
        this.name = name;
        this.purchaseCount = purchaseCount;
        this.purchasePrice = purchasePrice;
        this.receiver = receiver;
        this.address = address;
        this.messageToSend = messageToSend;
        this.imageUrl = imageUrl;
    }

    public Order(Long id, String sender,
                 String maker, String name,
                 Integer purchaseCount,
                 Long purchasePrice, String receiver,
                 String address, String messageToSend,
                 LocalDateTime createdAt,
                 String imageUrl) {
        this.id = id;
        this.sender = sender;
        this.maker = maker;
        this.name = name;
        this.purchaseCount = purchaseCount;
        this.purchasePrice = purchasePrice;
        this.receiver = receiver;
        this.address = address;
        this.messageToSend = messageToSend;
        this.createdAt = createdAt;
        this.imageUrl = imageUrl;
    }

    public OrderResultDto toOrderResultDto() {
        return new OrderResultDto(id, sender, name, purchaseCount, purchasePrice, receiver);
    }

    public OrderDto toDto() {
        return new OrderDto(
                id, maker, name, purchaseCount, purchasePrice,
                receiver, address, messageToSend,
                createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), imageUrl
        );
    }

    public Long id() {
        return id;
    }
}
