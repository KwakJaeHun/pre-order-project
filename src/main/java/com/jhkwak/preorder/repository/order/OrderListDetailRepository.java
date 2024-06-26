package com.jhkwak.preorder.repository.order;

import com.jhkwak.preorder.entity.order.OrderListDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderListDetailRepository extends JpaRepository<OrderListDetail, Long> {

    List<OrderListDetail> findByOrderConfirmAtBefore(LocalDate localDate);
}
