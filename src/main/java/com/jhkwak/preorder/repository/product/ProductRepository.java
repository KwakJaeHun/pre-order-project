package com.jhkwak.preorder.repository.product;

import com.jhkwak.preorder.entity.product.Product;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
