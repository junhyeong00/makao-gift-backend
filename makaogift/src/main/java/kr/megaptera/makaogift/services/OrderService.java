package kr.megaptera.makaogift.services;

import kr.megaptera.makaogift.exceptions.OrderFailed;
import kr.megaptera.makaogift.exceptions.OrderNotFound;
import kr.megaptera.makaogift.exceptions.ProductNotFound;
import kr.megaptera.makaogift.exceptions.UserNotFound;
import kr.megaptera.makaogift.models.Order;
import kr.megaptera.makaogift.models.Product;
import kr.megaptera.makaogift.models.User;
import kr.megaptera.makaogift.repositories.OrderRepository;
import kr.megaptera.makaogift.repositories.ProductRepository;
import kr.megaptera.makaogift.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository,
                        UserRepository userRepository,
                        ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public Order createOrder(String userName, Long productId,
                             Integer purchaseCount, Long purchasePrice,
                             String receiver, String address,
                             String messageToSend) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(UserNotFound::new);

        if (user.amount() < purchasePrice) {
            throw new OrderFailed("잔액이 부족하여 선물하기가 불가합니다");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFound::new);

        user.reduceAmount(purchasePrice);

        String sender = user.name();
        String maker = product.maker();
        String name = product.name();

        Order order = new Order(
               sender, maker, name, purchaseCount, purchasePrice,
                receiver, address, messageToSend
        );

        orderRepository.save(order);

        return order;
    }

    public Page<Order> findOrdersByUserName(String userName, Integer page) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(UserNotFound:: new);

        String sender = user.name();

        Pageable pageable = PageRequest.of(page - 1, 8);

        return orderRepository.findAllBySender(sender, pageable);
    }

    public Order orderDetail(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(OrderNotFound::new);
    }
}
