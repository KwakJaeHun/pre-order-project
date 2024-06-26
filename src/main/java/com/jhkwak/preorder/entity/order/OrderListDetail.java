package com.jhkwak.preorder.entity.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jhkwak.preorder.entity.TimeStamp;
import com.jhkwak.preorder.entity.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Entity
@Getter @Setter
@Table(name = "order_list_detail")
@NoArgsConstructor
public class OrderListDetail extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pro_duct_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_list_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private OrderList orderList;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private Character status;

    @Column(nullable = false)
    @ColumnDefault("'S'")
    private Character deliveryStatus;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate orderConfirmAt;

    public OrderListDetail(Product product, int quantity, Long price, Character status, Character deliveryStatus) {
        this.product = product;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
        this.deliveryStatus = deliveryStatus;
        this.orderConfirmAt = LocalDate.now();
    }
}
