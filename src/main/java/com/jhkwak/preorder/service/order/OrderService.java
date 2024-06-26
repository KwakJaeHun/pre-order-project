package com.jhkwak.preorder.service.order;

import com.jhkwak.preorder.dto.order.OrderCheckOutListResponseDto;
import com.jhkwak.preorder.dto.order.OrderListRequestDto;
import com.jhkwak.preorder.dto.order.OrderListResponseDto;
import com.jhkwak.preorder.dto.order.OrderProductResponseDto;
import com.jhkwak.preorder.dto.user.UserResponseDto;
import com.jhkwak.preorder.entity.order.OrderList;
import com.jhkwak.preorder.entity.order.OrderListDetail;
import com.jhkwak.preorder.entity.product.Product;
import com.jhkwak.preorder.entity.product.ProductStock;
import com.jhkwak.preorder.entity.user.User;
import com.jhkwak.preorder.repository.order.OrderListDetailRepository;
import com.jhkwak.preorder.repository.order.OrderListRepository;
import com.jhkwak.preorder.repository.product.ProductRepository;
import com.jhkwak.preorder.repository.product.ProductStockRepository;
import com.jhkwak.preorder.repository.user.CartRepository;
import com.jhkwak.preorder.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductStockRepository productStockRepository;
    private final OrderListRepository orderListRepository;
    private final CartRepository cartRepository;
    private final OrderListDetailRepository orderListDetailRepository;


    // 주문 진행 페이지
    public OrderCheckOutListResponseDto checkOutPage(Long userId, List<String> items) {
        User user = userRepository.findById(userId).get();
        
        // 고객 정보 저장
        UserResponseDto userResponseDto = new UserResponseDto(user);

        // 파라미터로 넘어온 주문 상품과 수량
        Map<Long, Integer> productIdQuantityMap = new HashMap<>();
        for (String item : items) {
            String[] parts = item.split(":");
            Long productId = Long.parseLong(parts[0]);
            Integer quantity = Integer.parseInt(parts[1]);
            productIdQuantityMap.put(productId, quantity);
        }
        
        // 상품 정보 가져오기
        List<Long> productIds = new ArrayList<>(productIdQuantityMap.keySet());
        List<Product> products = productRepository.findByIdIn(productIds);
        
        // 주문 상풍 정보 매핑
        List<OrderProductResponseDto> orderProductResponseDto  =  products.stream()
                .map(product -> {
                    OrderProductResponseDto dto = new OrderProductResponseDto();
                    dto.setProductId(product.getId());
                    dto.setName(product.getName());
                    dto.setPrice(product.getPrice() * productIdQuantityMap.get(product.getId()));
                    dto.setQuantity(productIdQuantityMap.get(product.getId()));
                    dto.setThumbnailImage(product.getThumbnailImage());
                    return dto;
                })
                .toList();

        // 주문 정보 return
        return new OrderCheckOutListResponseDto(userResponseDto, orderProductResponseDto);
    }

    @Transactional
    public void checkOut(Long userId, List<OrderListRequestDto> orderListRequestDto) {
        
        // 고객 정보
        User user = userRepository.findById(userId).get();
        
        // 주문 상세 만들기
        List<OrderListDetail> orderListDetails  =  orderListRequestDto.stream()
                .map(orderListData -> {
                    Product product = productRepository.findById(orderListData.getProductId()).get();
                    OrderListDetail orderListDetail = new OrderListDetail(
                            product,
                            orderListData.getQuantity(),
                            orderListData.getPrice(),
                            'Y',
                            'S'
                    );
                    return orderListDetail;
                })
                .toList();


        // 주문 정보 생성
        OrderList orderList = new OrderList(user, orderListDetails, 'Y');
        orderListRepository.save(orderList);
        
        // 아래 for문에 통합
//        List<Long> productIds = orderListRequestDto.stream()
//                .map(OrderListRequestDto::getProductId)
//                .toList();
//
//        cartRepository.deleteByUserIdAndProductIdIn(userId, productIds);

        
        for(OrderListRequestDto dto : orderListRequestDto){
            // 주문된 상품은 장바구니에서 삭제
            cartRepository.deleteByUserIdAndProductId(userId, dto.getProductId());
            
            // 재고 수량 업데이트
            ProductStock productStock = productStockRepository.findById(dto.getProductId()).get();
            if(productStock.getStockQuantity() - dto.getQuantity() > 0){
                productStock.setStockQuantity(productStock.getStockQuantity() - dto.getQuantity());
            }
            else{
                productStock.setStockQuantity(0);
            }
        }
    }

    // 주문 정보 list
    public List<OrderListResponseDto> orderList(Long userId) {

        List<OrderList> orderLists = orderListRepository.findByUserId(userId);

        return orderLists.stream()
                    .map(orderList -> {
                        OrderListResponseDto dto = new OrderListResponseDto();
                        dto.setOrderListId(orderList.getId());
                        dto.setStatus(orderList.getStatus());
                        dto.setCreatedAt(orderList.getCreatedAt());
                        dto.setOrderListDetails(orderList.getOrderListDetails());

                        // JsonIgnore Annotation 으로 대체
//                        ArrayList<Map<String, Object>> orderListDetails = new ArrayList<>();
//                        orderList.getOrderListDetails().stream()
//                                .forEach(orderListDetail -> {
//                                    Map<String, Object> detailMap = new HashMap<>();
//                                    detailMap.put("orderListDetailId", orderListDetail.getId());
//                                    detailMap.put("productId", orderListDetail.getProduct().getId());
//                                    detailMap.put("quantity", orderListDetail.getQuantity());
//                                    detailMap.put("price", orderListDetail.getPrice());
//                                    detailMap.put("status", orderListDetail.getStatus());
//                                    detailMap.put("deliveryStatus", orderListDetail.getDeliveryStatus());
//                                    detailMap.put("creatAt", orderListDetail.getCreatedAt());
//                                    orderListDetails.add(detailMap);
//                                });
//
//                        dto.setOrderListDetails(orderListDetails);

                        return dto;
                    })
                .toList();
    }
    
    // 자정에 배송 상태 없데이트
    @Transactional
    public void deliveryStatusUpdate() {
        LocalDate now = LocalDate.now();

        // 배송 중으로 업데이트 
        List<OrderListDetail> minusOneDay = orderListDetailRepository
                .findByOrderConfirmAtBefore(now);

        for (OrderListDetail detail : minusOneDay) {
            detail.setDeliveryStatus('D');
        }

        // 배송 완료로 업데이트
        List<OrderListDetail> minusTwoDay = orderListDetailRepository
                .findByOrderConfirmAtBefore(now.minusDays(1));

        for (OrderListDetail detail : minusTwoDay) {
            detail.setDeliveryStatus('Y');
        }

    }
}
