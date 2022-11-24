package kr.megaptera.makaogift.dtos;

public class OrderDto {
    private final Long id;

    private final String maker;

    private final String name;

    private final Integer purchaseCount;

    private final Long purchasePrice;

    private final String receiver;

    private final String address;

    private final String messageToSend;

    private final String createdAt;

    private final String imageUrl;

    public OrderDto(Long id, String maker,
                    String name, Integer purchaseCount,
                    Long purchasePrice, String receiver,
                    String address, String messageToSend,
                    String createdAt, String imageUrl) {
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public String getMaker() {
        return maker;
    }

    public String getName() {
        return name;
    }

    public Integer getPurchaseCount() {
        return purchaseCount;
    }

    public Long getPurchasePrice() {
        return purchasePrice;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getAddress() {
        return address;
    }

    public String getMessageToSend() {
        return messageToSend;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
