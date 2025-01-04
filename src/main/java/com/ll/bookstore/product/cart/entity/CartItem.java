package com.ll.bookstore.product.cart.entity;

import com.ll.bookstore.global.jpa.BaseEntity;
import com.ll.bookstore.member.member.entity.Member;
import com.ll.bookstore.product.product.entity.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    private Member member;
    @OneToOne
    private Product product;
}
