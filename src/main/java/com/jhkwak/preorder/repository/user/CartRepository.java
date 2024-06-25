package com.jhkwak.preorder.repository.user;

import com.jhkwak.preorder.entity.user.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUserIdAndProductId(Long id, Long id1);
}