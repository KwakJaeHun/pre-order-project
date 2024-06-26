package com.jhkwak.preorder.controller;

import com.jhkwak.preorder.dto.order.OrderCheckOutListResponseDto;
import com.jhkwak.preorder.dto.order.OrderListRequestDto;
import com.jhkwak.preorder.dto.order.OrderListResponseDto;
import com.jhkwak.preorder.entity.order.OrderList;
import com.jhkwak.preorder.security.UserDetailImpl;
import com.jhkwak.preorder.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    
    // 주문 진행 페이지
     @GetMapping("/check-out")
     public ResponseEntity<?> checkOutPage(
         @AuthenticationPrincipal UserDetailImpl userDetail,
         @RequestParam("item[]") List<String> items
     )
     {
         OrderCheckOutListResponseDto orderCheckOutListResponseDto = orderService.checkOutPage(userDetail.getId(), items);
         return ResponseEntity.ok(orderCheckOutListResponseDto);
     }
     
     // 주문 진행
    @PostMapping("/check-out")
    public ResponseEntity<?> checkOut(
        @AuthenticationPrincipal UserDetailImpl userDetail,
        @RequestBody List<OrderListRequestDto> orderListRequestDto
    )
    {
        orderService.checkOut(userDetail.getId(), orderListRequestDto);
        return ResponseEntity.ok().build();
    }

    // 주문리스트
    @GetMapping("/order-list")
    public ResponseEntity<?> orderList(
            @AuthenticationPrincipal UserDetailImpl userDetail
    )
    {
        List<OrderListResponseDto> orderListResponseDto = orderService.orderList(userDetail.getId());
        return ResponseEntity.ok(orderListResponseDto);
    }
}
