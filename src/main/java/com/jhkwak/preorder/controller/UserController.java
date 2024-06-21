package com.jhkwak.preorder.controller;

import com.jhkwak.preorder.dto.user.LoginRequestDto;
import com.jhkwak.preorder.dto.user.SignupRequestDto;
import com.jhkwak.preorder.entity.Response;
import com.jhkwak.preorder.service.user.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    @PostMapping("/signup")
    @ResponseBody
    public Response signup(SignupRequestDto requestDto){
        return userService.signup(requestDto);
    }

    @PostMapping("/login")
    public String login(LoginRequestDto loginRequestDto, HttpServletResponse res){
        try{
            userService.login(loginRequestDto, res);
        }
        catch(Exception e){
            return "redirect:/user/login-page?error";
        }

        return "redirect:/";
    }
}
