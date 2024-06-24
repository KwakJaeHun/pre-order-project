package com.jhkwak.preorder.controller;

import com.jhkwak.preorder.dto.product.WishRequestDto;
import com.jhkwak.preorder.dto.user.*;
import com.jhkwak.preorder.entity.Response;
import com.jhkwak.preorder.entity.ResponseCode;
import com.jhkwak.preorder.jwt.JwtUtil;
import com.jhkwak.preorder.service.user.CartService;
import com.jhkwak.preorder.service.user.MyPageService;
import com.jhkwak.preorder.service.user.WishListService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/my-page")
public class MyPageController {

    private final MyPageService myPageService;
    private final JwtUtil jwtUtil;
    private final WishListService wishListService;
    private final CartService cartService;

    // 메인
    @GetMapping("")
    public ResponseEntity<?> signup(
        @CookieValue(value = JwtUtil.AUTHORIZATION_HEADER, required = false) String tokenValue,
        HttpServletResponse res
    )
    {
        Long jwtValidate = jwtUtil.validateToken(tokenValue, res);

        if(jwtValidate != 0L){
            List<UserResponseDto> userInfo  = myPageService.getUserInfo(jwtValidate);
            return ResponseEntity.ok(userInfo);
        }
        else{
            return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, "/user/login").build();
        }
    }

    // 주소 업데이트
    @PutMapping("/address")
    public ResponseEntity<?> updateAddress(
        @CookieValue(value = JwtUtil.AUTHORIZATION_HEADER, required = false) String tokenValue,
        HttpServletResponse res,
        @RequestBody InfoUpdateRequestDto infoUpdateRequestDto
    )
    {

        Long jwtValidate = jwtUtil.validateToken(tokenValue, res);

        if(jwtValidate != 0L){
            myPageService.updateUserInfo(jwtValidate, infoUpdateRequestDto);
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, "/user/login").build();
        }
    }

    // 전화번호 업데이트
    @PutMapping("/phone")
    public ResponseEntity<?> updatePhone(
        @CookieValue(value = JwtUtil.AUTHORIZATION_HEADER, required = false) String tokenValue,
        HttpServletResponse res,
        @RequestBody InfoUpdateRequestDto infoUpdateRequestDto
    )
    {

        Long jwtValidate = jwtUtil.validateToken(tokenValue, res);

        if(jwtValidate != 0L){
            myPageService.updateUserInfo(jwtValidate, infoUpdateRequestDto);
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, "/user/login").build();
        }
    }

    // 비밀번호 업데이트
    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(
        @CookieValue(value = JwtUtil.AUTHORIZATION_HEADER, required = false) String tokenValue,
        HttpServletResponse res,
        @RequestBody InfoUpdateRequestDto infoUpdateRequestDto
    )
    {

        Long jwtValidate = jwtUtil.validateToken(tokenValue, res);

        if(jwtValidate != 0L){
            if(myPageService.updateUserInfo(jwtValidate, infoUpdateRequestDto)){
                return ResponseEntity.ok().build();
            }
            else{
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new Response(ResponseCode.USER_PASSWORD_WRONG));
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, "/user/login").build();
        }
    }

    // wishList
    @GetMapping("/wish-list")
    public ResponseEntity<?> wishList(
        @CookieValue(value = JwtUtil.AUTHORIZATION_HEADER, required = false) String tokenValue,
        HttpServletResponse res
    )
    {

        Long jwtValidate = jwtUtil.validateToken(tokenValue, res);
        if(jwtValidate != 0L){
            List<WishListResponseDto> wishlist = wishListService.getWishList(jwtValidate);
            return ResponseEntity.ok(wishlist);

        }
        else{
            return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, "/user/login").build();
        }
    }

    // wish 삭제
    @DeleteMapping("/wish-delete")
    public ResponseEntity<?> wishDelete(
            @CookieValue(value = JwtUtil.AUTHORIZATION_HEADER, required = false) String tokenValue,
            HttpServletResponse res,
            @RequestBody WishRequestDto wishRequestDto
    )
    {

        Long jwtValidate = jwtUtil.validateToken(tokenValue, res);
        if(jwtValidate != 0L){
            List<WishListResponseDto> wishlist = wishListService.wishDelete(jwtValidate, wishRequestDto);
            return ResponseEntity.ok(wishlist);

        }
        else{
            return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, "/user/login").build();
        }
    }

    // 장바구니
    @GetMapping("/cart-list")
    public ResponseEntity<?> cartList(
        @CookieValue(value = JwtUtil.AUTHORIZATION_HEADER, required = false) String tokenValue,
        HttpServletResponse res
    )
    {
        Long jwtValidate = jwtUtil.validateToken(tokenValue, res);
        if(jwtValidate != 0L){
            List<CartResponseDto> cartList = cartService.getCartList(jwtValidate);
            return ResponseEntity.ok(cartList);

        }
        else{
            return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, "/user/login").build();
        }
    }

    // 장바구니 추가
    @PostMapping("/cart-add")
    public ResponseEntity<?> cartRegistration(
            @CookieValue(value = JwtUtil.AUTHORIZATION_HEADER, required = false) String tokenValue,
            HttpServletResponse res,
            @RequestBody CartRequestDto cartRequestDto
    )
    {
        Long jwtValidate = jwtUtil.validateToken(tokenValue, res);
        if(jwtValidate != 0L){
            List<CartResponseDto> cartList = cartService.cartRegistration(jwtValidate, cartRequestDto);
            return ResponseEntity.ok(cartList);

        }
        else{
            return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, "/user/login").build();
        }
    }

    // 장바구니 업데이트
    @PutMapping("/cart-update")
    public ResponseEntity<?> cartUpdate(
            @CookieValue(value = JwtUtil.AUTHORIZATION_HEADER, required = false) String tokenValue,
            HttpServletResponse res,
            @RequestBody CartRequestDto cartRequestDto
    )
    {
        Long jwtValidate = jwtUtil.validateToken(tokenValue, res);
        if(jwtValidate != 0L){
            List<CartResponseDto> cartList = cartService.cartUpdate(jwtValidate, cartRequestDto);
            return ResponseEntity.ok(cartList);

        }
        else{
            return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, "/user/login").build();
        }
    }

    // 장바구니 삭제
    @DeleteMapping("/cart-delete")
    public ResponseEntity<?> cartDelete(
            @CookieValue(value = JwtUtil.AUTHORIZATION_HEADER, required = false) String tokenValue,
            HttpServletResponse res,
            @RequestBody CartRequestDto cartRequestDto
    )
    {
        Long jwtValidate = jwtUtil.validateToken(tokenValue, res);
        if(jwtValidate != 0L){
            List<CartResponseDto> cartList = cartService.cartDelete(jwtValidate, cartRequestDto);
            return ResponseEntity.ok(cartList);
        }
        else{
            return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, "/user/login").build();
        }
    }
}
