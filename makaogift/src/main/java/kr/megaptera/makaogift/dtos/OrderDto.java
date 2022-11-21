package kr.megaptera.makaogift.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class OrderDto {
    private final Long productId;

    private final Integer purchaseCount;

    private final Long purchasePrice;

    @NotBlank( message = "성함을 입력해주세요")
    @Pattern(
            regexp = "^[가-힣]{3,7}$",
            message = "3-7자까지 한글만 사용 가능합니다"
    )
    private final String receiver;

    @NotBlank(message = "주소를 입력해주세요")
    private final String address;

    private final String messageToSend;

    public OrderDto(Long productId,
                    Integer purchaseCount,
                    Long purchasePrice,
                    String receiver,
                    String address,
                    String messageToSend) {
        this.productId = productId;
        this.purchaseCount = purchaseCount;
        this.purchasePrice = purchasePrice;
        this.receiver = receiver;
        this.address = address;
        this.messageToSend = messageToSend;
    }

    public Long getProductId() {
        return productId;
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
}
