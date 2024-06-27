package com.jhkwak.preorder.controller;

import com.jhkwak.preorder.dto.user.SignupRequestDto;
import com.jhkwak.preorder.jwt.JwtUtil;
import com.jhkwak.preorder.service.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
class UserControllerTest {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    @DisplayName("회원가입 테스트")
    @Transactional
    void signupTest(){
        SignupRequestDto requestDto = new SignupRequestDto();
        requestDto.setName("곽재훈");
        requestDto.setEmail("zzddtt73@gmail.com");
        requestDto.setAddress("봇들마을");
        requestDto.setPassword("test");
        requestDto.setPhone("01037848809");

        UserController us = new UserController(userService, jwtUtil);
        System.out.println(us.signup(requestDto));

    }
}