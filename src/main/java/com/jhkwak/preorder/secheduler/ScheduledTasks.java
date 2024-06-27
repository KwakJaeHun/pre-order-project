package com.jhkwak.preorder.secheduler;

import com.jhkwak.preorder.entity.order.OrderListDetail;
import com.jhkwak.preorder.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    private final OrderService orderService;

    // 매일 자정에 실행
    // 배송중, 배송 완료 상태값 업데이트 스케줄러
    @Scheduled(cron = "*/1 * * * * ?")
    public void performTaskAtMidnight() {
        orderService.deliveryStatusUpdate();
        System.out.println("스케줄러 접근");
    }
}
