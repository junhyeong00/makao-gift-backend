package kr.megaptera.makaogift.repositories;

import kr.megaptera.makaogift.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
