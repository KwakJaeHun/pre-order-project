package com.jhkwak.preorder.service.user;

import com.jhkwak.preorder.dto.user.UserResponseDto;
import com.jhkwak.preorder.entity.user.User;
import com.jhkwak.preorder.repository.user.MyPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final MyPageRepository myPageRepository;

    public List<UserResponseDto> getUserInfo(Long info) {
        return myPageRepository.findById(info).stream().map(UserResponseDto::new).toList();
    }
}
