package com.jhkwak.preorder.dto.product;

import com.jhkwak.preorder.entity.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor
public class ProductResponseDto {
    private String name;
    private Long price;
    private String thumbnailImage;
    private Character soldOutStatus;
    private Character newStatus;
    private Character bestStatus;

    public ProductResponseDto(Product product){
        this.name = product.getName();
        this.price = product.getPrice();
        this.thumbnailImage = product.getThumbnailImage();
        this.soldOutStatus = product.getSoldOutStatus();
        this.newStatus = product.getNewStatus();
        this.bestStatus = product.getBestStatus();

    }
}
