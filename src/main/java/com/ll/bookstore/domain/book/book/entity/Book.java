package com.ll.bookstore.domain.book.book.entity;


import com.ll.bookstore.global.jpa.BaseEntity;
import com.ll.bookstore.domain.member.member.entity.Member;
import com.ll.bookstore.domain.product.product.entity.Product;
import com.ll.bookstore.standard.utill.Ut.Ut;
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
public class Book extends BaseEntity {

    @ManyToOne
    private Member author;
    @OneToOne
    private Product product;
    private String title;
    private String body;
    private int price;

    public String getModelName() {
        return Ut.str.lcfirst(this.getClass().getSimpleName());
    }
}
