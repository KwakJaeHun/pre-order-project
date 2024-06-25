package com.jhkwak.preorder.service.user;

import com.jhkwak.preorder.dto.user.CartRequestDto;
import com.jhkwak.preorder.dto.user.CartResponseDto;
import com.jhkwak.preorder.entity.product.Product;
import com.jhkwak.preorder.entity.user.Cart;
import com.jhkwak.preorder.entity.user.User;
import com.jhkwak.preorder.repository.product.ProductRepository;
import com.jhkwak.preorder.repository.user.CartRepository;
import com.jhkwak.preorder.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public List<CartResponseDto> getCartList(Long userId) {
        User user = findByuser(userId);
        List<Cart> cartLists = user.getCarts();

        return cartLists.stream()
                .map(cartList -> new CartResponseDto(
                        cartList.getProduct().getId(),
                        cartList.getProduct().getName(),
                        cartList.getCount(),
                        cartList.getProduct().getPrice() * cartList.getCount(),
                        cartList.getProduct().getProductStock().getStockQuantity())
                ).toList();

    }

    // 장바구니 등록
    @Transactional
    public List<CartResponseDto> cartRegistration(Long userId, CartRequestDto cartRequestDto) {

        User user = findByuser(userId);
        Product product = findByProduct(cartRequestDto.getProductId());

        Optional<Cart> cartExist = cartRepository.findByUserIdAndProductId(userId, product.getId());
        // 장바구니에 동일한 상품이 존재하면 수량 더해서 업데이트
        if(cartExist.isPresent()){
            Cart cart = cartExist.get();
            int sumCount = cart.getCount() + cartRequestDto.getCount();
            cart.setCount(sumCount);
        }
        // 없으면 새로 등록
        else{
            Cart cart = new Cart(user, product, cartRequestDto.getCount());
            cartRepository.save(cart);
        }

        return getCartList(userId);
    }
    
    // 장바구니 업데이트
    @Transactional
    public List<CartResponseDto> cartUpdate(Long userId, CartRequestDto cartRequestDto) {

        Product product = findByProduct(cartRequestDto.getProductId());

        Optional<Cart> cartExist = cartRepository.findByUserIdAndProductId(userId, product.getId());
        // 장바구니에 동일한 상품이 존재하면 수량 업데이트
        if(cartExist.isPresent()){
            Cart cart = cartExist.get();
            cart.setCount(cartRequestDto.getCount());
        }

        return getCartList(userId);
    }
    
    // 장바구니 삭제
    public List<CartResponseDto> cartDelete(Long userId, CartRequestDto cartRequestDto) {

        Product product = findByProduct(cartRequestDto.getProductId());

        Optional<Cart> cartExist = cartRepository.findByUserIdAndProductId(userId, product.getId());
        // 장바구니에 상품이 있으면 상품 삭제
        if(cartExist.isPresent()){
            cartRepository.delete(cartExist.get());
        }

        return getCartList(userId);
    }

    private User findByuser(Long userId) {
        return userRepository.findById(userId).get();
    }

    private Product findByProduct(Long productId){
        return productRepository.findById(productId).get();
    }
}
