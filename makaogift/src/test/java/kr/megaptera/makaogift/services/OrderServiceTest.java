package kr.megaptera.makaogift.services;

import kr.megaptera.makaogift.models.Order;
import kr.megaptera.makaogift.models.Product;
import kr.megaptera.makaogift.models.User;
import kr.megaptera.makaogift.repositories.OrderRepository;
import kr.megaptera.makaogift.repositories.ProductRepository;
import kr.megaptera.makaogift.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class OrderServiceTest {
    OrderService orderService;
    OrderRepository orderRepository;
    UserRepository userRepository;
    ProductRepository productRepository;

    @BeforeEach
    void setup() {
        orderRepository = mock(OrderRepository.class);
        userRepository = mock(UserRepository.class);
        productRepository = mock(ProductRepository.class);

        orderService = new OrderService(orderRepository, userRepository, productRepository);
    }

    @Test
    void createOrder() {
        String userName = "test123";
        Integer purchaseCount = 3;
        Long purchasePrice = 30000L;
        String receiver = "산토끼";
        String address = "뉴욕";
        String messageToSend = "good";

        Long productId = 1L;
        String maker = "산";
        String productName = "딸기";
        Long price = 10000L;
        String description = "good";

        User user = new User(1L, userName, "배준형", 50000L);

        Product product = new Product(productId, maker, productName,price, description);

        given(userRepository.findByUserName(userName))
                .willReturn(Optional.of(user));
        given(productRepository.findById(productId))
                .willReturn(Optional.of(product));

        Order order = orderService.createOrder(userName,
                productId, purchaseCount,
               purchasePrice,receiver,
               address, messageToSend);

        assertThat(order).isNotNull();
    }
}
