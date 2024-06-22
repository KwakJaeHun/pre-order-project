package com.jhkwak.preorder.controller;

import com.jhkwak.preorder.dto.user.InfoUpdateRequestDto;
import com.jhkwak.preorder.dto.user.UserResponseDto;
import com.jhkwak.preorder.entity.Response;
import com.jhkwak.preorder.entity.ResponseCode;
import com.jhkwak.preorder.jwt.JwtUtil;
import com.jhkwak.preorder.service.user.MyPageService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/myPage")
public class MyPageController {

    private final MyPageService myPageService;
    private final JwtUtil jwtUtil;

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
}
