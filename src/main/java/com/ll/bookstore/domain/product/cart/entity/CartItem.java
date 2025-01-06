package com.ll.bookstore.domain.product.cart.entity;

import com.ll.bookstore.global.jpa.BaseEntity;
import com.ll.bookstore.domain.member.member.entity.Member;
import com.ll.bookstore.domain.product.product.entity.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Builder
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@Setter
@Getter
@ToString(callSuper = true)
public class CartItem extends BaseEntity {

    @ManyToOne
    private Member buyer;
    @ManyToOne
    private Product product;
}
