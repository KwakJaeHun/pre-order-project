package com.jhkwak.preorder.repository.product;

import com.jhkwak.preorder.entity.product.ProductStock;
import org.apache.el.lang.ELArithmetic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {
}
