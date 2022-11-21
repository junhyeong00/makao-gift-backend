package kr.megaptera.makaogift.dtos;

public class OrderResultDto {
    private Long orderId;

    private String sender;

    private String name;

    private Integer purchaseCount;

    private Long purchasePrice;

    private String receiver;

    public OrderResultDto(Long orderId,
                          String sender,
                          String name,
                          Integer purchaseCount,
                          Long purchasePrice,
                          String receiver) {
        this.orderId = orderId;
        this.sender = sender;
        this.name = name;
        this.purchaseCount = purchaseCount;
        this.purchasePrice = purchasePrice;
        this.receiver = receiver;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getSender() {
        return sender;
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
}
