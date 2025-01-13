package com.ll.bookstore.domain.product.product.entity;

import com.ll.bookstore.domain.book.book.entity.Book;
import com.ll.bookstore.domain.member.member.entity.Member;
import com.ll.bookstore.global.app.AppConfig;
import com.ll.bookstore.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Getter
@ToString(callSuper = true)
public class Product extends BaseEntity {
    @ManyToOne
    private Member maker;
    private String relTypeCode;
    private long relId;
    private String name;
    private int price;

    public Book getBook() {
        return AppConfig.getEntityManager().getReference(Book.class, relId);
    }
}