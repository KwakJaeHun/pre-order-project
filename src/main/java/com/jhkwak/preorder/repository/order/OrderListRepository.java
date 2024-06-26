package com.jhkwak.preorder.repository.order;

import com.jhkwak.preorder.entity.order.OrderList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderListRepository extends JpaRepository<OrderList, Long> {
    List<OrderList> findByUserId(Long userId);
}
