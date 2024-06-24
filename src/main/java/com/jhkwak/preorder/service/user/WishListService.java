package com.jhkwak.preorder.service.user;

import com.jhkwak.preorder.dto.product.WishRequestDto;
import com.jhkwak.preorder.dto.user.WishListResponseDto;
import com.jhkwak.preorder.entity.user.User;
import com.jhkwak.preorder.entity.user.WishList;
import com.jhkwak.preorder.repository.user.UserRepository;
import com.jhkwak.preorder.repository.user.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishListService {

    private final UserRepository userRepository;
    private final WishListRepository wishListRepository;

    public List<WishListResponseDto> getWishList(Long userId) {

        User user = userRepository.findById(userId).get();

        List<WishList> wishLists = user.getWishes();

        return wishLists.stream()
                .map(wishList -> new WishListResponseDto(
                        wishList.getProduct().getId(),
                        wishList.getProduct().getName(),
                        wishList.getProduct().getPrice().longValue()
                ))
                .collect(Collectors.toList());

        // List<Map<String, Object>> results  = wishListRepository.findUserWishList(userId);
        // return results.stream()
        //             .map(result -> new WishListResponseDto(
        //                     (String) result.get("productName"),
        //                     Long.parseLong(String.valueOf(result.get("productPrice")))
        //             )).toList();
    }

    public List<WishListResponseDto> wishDelete(Long userId, WishRequestDto wishRequestDto) {
        WishList wish = wishListRepository.findByUserIdAndProductId(userId, wishRequestDto.getProductId());

        wishListRepository.delete(wish);

        return getWishList(userId);
    }
}
