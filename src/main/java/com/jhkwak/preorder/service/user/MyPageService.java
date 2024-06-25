package com.jhkwak.preorder.service.user;

import com.jhkwak.preorder.dto.user.InfoUpdateRequestDto;
import com.jhkwak.preorder.dto.user.UserResponseDto;
import com.jhkwak.preorder.entity.Response;
import com.jhkwak.preorder.entity.user.User;
import com.jhkwak.preorder.repository.user.MyPageRepository;
import com.jhkwak.preorder.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponseDto> getUserInfo(Long userId) {
        return userRepository.findById(userId).stream().map(UserResponseDto::new).toList();
    }

    @Transactional
    public boolean updateUserInfo(Long Id, InfoUpdateRequestDto infoUpdateRequestDto) {

        User user = userRepository.findById(Id).get();

        if((infoUpdateRequestDto.getAddress() != null)){
            user.setAddress(infoUpdateRequestDto.getAddress());
        }

        if((infoUpdateRequestDto.getPhone() != null)){
            user.setPhone(infoUpdateRequestDto.getPhone());
        }

        if((infoUpdateRequestDto.getUpdatePassword() != null)){
            
            // 비밀번호가 일치하면 비밀번호 업데이트
            if(passwordEncoder.matches(infoUpdateRequestDto.getCurrentPassword(), user.getPassword())){
                String updatePassword = passwordEncoder.encode(infoUpdateRequestDto.getUpdatePassword());
                user.setPassword(updatePassword);
            }
            else{
                return false;
            }
        }
        
        return true;
    }
}
