package com.jhkwak.preorder.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartRequestDto {
    private Long productId;
    private int count;
}
