package com.jhkwak.preorder.service.user;

import com.jhkwak.preorder.dto.user.LoginRequestDto;
import com.jhkwak.preorder.dto.user.SignupRequestDto;
import com.jhkwak.preorder.entity.Response;
import com.jhkwak.preorder.entity.ResponseCode;
import com.jhkwak.preorder.entity.user.User;
import com.jhkwak.preorder.jwt.JwtUtil;
import com.jhkwak.preorder.repository.user.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public Response signup(SignupRequestDto requestDto) {

        String name     = requestDto.getName();
        String email    = requestDto.getEmail();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String phone    = requestDto.getPhone();
        String address  = requestDto.getAddress();
        
        // 이메일 중복확인
        Optional<User> checkEmail = userRepository.findByEmail(email);
        
        if(checkEmail.isPresent()){
            return new Response(ResponseCode.USER_EMAIL_ALREADY_EXIST);
            // throw new IllegalArgumentException("이미 회원가입이 완료된 이메일 입니다.");
        }

        // 핸드폰 번호 중복확인
        Optional<User> checkPhone = userRepository.findByPhone(phone);

        if(checkPhone.isPresent()){
            return new Response(ResponseCode.USER_PHONE_ALREADY_EXIST);
            //throw new IllegalArgumentException("이미 회원가입이 완료된 핸드폰 번호 입니다.");
        }
        
        // 사용자 등록
        User user = new User(name, email, password, phone, address);
        userRepository.save(user);

        return new Response(ResponseCode.USER_CREATE_SUCCESS);
    }

    public void login(LoginRequestDto loginRequestDto, HttpServletResponse res) {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        // 비밀번호 확인
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 생성 및 쿠키에 저장 후 Response 객체에 추가
        String token = jwtUtil.createToken(user.getEmail());
        jwtUtil.addJwtToCookie(token, res);

    }
}
