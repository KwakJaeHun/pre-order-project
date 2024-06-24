package com.jhkwak.preorder.controller;

import com.jhkwak.preorder.dto.product.ProductDetailResponseDto;
import com.jhkwak.preorder.dto.product.ProductRegRequestDto;
import com.jhkwak.preorder.dto.product.ProductResponseDto;
import com.jhkwak.preorder.dto.product.WishRequestDto;
import com.jhkwak.preorder.entity.product.Product;
import com.jhkwak.preorder.jwt.JwtUtil;
import com.jhkwak.preorder.service.product.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final JwtUtil jwtUtil;
    
    @GetMapping("/list")
    public List<ProductResponseDto> productList(){
        return productService.productList();
    }

    @GetMapping("/detail/{productId}")
    public List<ProductDetailResponseDto> productDetail(@PathVariable Long productId){
        return productService.productDetail(productId);
    }

    @PostMapping("/registration")
    public Product productRegistration(@RequestBody ProductRegRequestDto productRegRequestDto){
        return productService.productRegistration(productRegRequestDto);
    }

    @PostMapping("/wish-add")
    public ResponseEntity<?> wishAdd(
            @RequestBody WishRequestDto wishRequestDto,
            @CookieValue(value = JwtUtil.AUTHORIZATION_HEADER, required = false) String tokenValue,
            HttpServletResponse res
    )
    {

        Long jwtValidate = jwtUtil.validateToken(tokenValue, res);

        if(jwtValidate != 0L){
            productService.wishAdd(jwtValidate, wishRequestDto);
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, "/user/login").build();
        }
    }
}
