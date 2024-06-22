package com.jhkwak.preorder.controller;

import com.jhkwak.preorder.dto.user.UserResponseDto;
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

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/myPage")
public class MyPageController {

    private final MyPageService myPageService;
    private final JwtUtil jwtUtil;

    // 메인
    @GetMapping("")
    public ResponseEntity<?> signup(
            @CookieValue(value = JwtUtil.AUTHORIZATION_HEADER, required = false) String tokenValue,
            HttpServletResponse response
    )
    {
        boolean jwtValidate = jwtUtil.validateToken(tokenValue, response);

        if(jwtValidate){
            Long info = Long.parseLong(jwtUtil.getUserInfoFromToken(jwtUtil.getTokenValue()).getSubject());
            List<UserResponseDto> userInfo  = myPageService.getUserInfo(info);
            return ResponseEntity.ok(userInfo);
        }
        else{
            return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, "/user/login").build();
        }
    }
}
