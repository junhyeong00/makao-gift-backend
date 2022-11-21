package kr.megaptera.makaogift.services;

import kr.megaptera.makaogift.models.Order;
import kr.megaptera.makaogift.models.Product;
import kr.megaptera.makaogift.models.User;
import kr.megaptera.makaogift.repositories.OrderRepository;
import kr.megaptera.makaogift.repositories.ProductRepository;
import kr.megaptera.makaogift.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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

        Product product = new Product(productId, maker, productName, price, description);

        given(userRepository.findByUserName(userName))
                .willReturn(Optional.of(user));
        given(productRepository.findById(productId))
                .willReturn(Optional.of(product));

        Order order = orderService.createOrder(userName,
                productId, purchaseCount,
                purchasePrice, receiver,
                address, messageToSend);

        assertThat(order).isNotNull();
    }

    @Test
    void findOrdersByUserName() {
        String userName = "test123";
        String name = "김토끼";
        User user = new User(1L, userName, name, 10000L);

        given(userRepository.findByUserName(userName))
                .willReturn(Optional.of(user));

        List<Order> orders = List.of(
                new Order(1L, name, "제조사 1", "상품 1", 2, 3000L, "산토끼", "산", "안녕"),
                new Order(2L, name, "제조사 2", "상품 2", 2, 3000L, "산토끼", "산", "안녕"),
                new Order(3L, name, "제조사 3", "상품 3", 2, 3000L, "산토끼", "산", "안녕")
        );

        int page = 1;
        int pageSize = 2;
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        Page<Order> pageableOrders
                = new PageImpl<>(orders, pageable, orders.size());

        given(orderRepository.findAllBySender(eq(name), any(Pageable.class)))
                .willReturn(pageableOrders);

        Page<Order> founds = orderService.findOrdersByUserName(userName, page);

        assertThat(founds).hasSize(orders.size());

        verify(userRepository).findByUserName(userName);
        verify(orderRepository).findAllBySender(eq(name), any(Pageable.class));
    }
}
