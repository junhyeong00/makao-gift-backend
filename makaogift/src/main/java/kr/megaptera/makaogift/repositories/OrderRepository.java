package kr.megaptera.makaogift.repositories;

import kr.megaptera.makaogift.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order save(Order order);

    // TODO 동명이인인 경우 해결
    Page<Order> findAllBySender(String sender, Pageable pageable);
}
