package com.ll.bookstore.domain.member.mybook.entity;

import com.ll.bookstore.domain.book.book.entity.Book;
import com.ll.bookstore.domain.member.member.entity.Member;
import com.ll.bookstore.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
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
public class MyBook extends BaseEntity {

    @ManyToOne
    private Member owner;
    @ManyToOne
    private Book book;
}
