package com.ll.bookstore.domain.member.member.entity;


import com.ll.bookstore.domain.book.book.entity.Book;
import com.ll.bookstore.domain.member.mybook.entity.MyBook;
import com.ll.bookstore.domain.product.order.entity.Order;
import com.ll.bookstore.global.jpa.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Builder
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@Setter
@Getter
@ToString(callSuper = true)
public class Member extends BaseEntity {
    private String username;
    private String password;
    private long restCash;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MyBook> myBooks = new ArrayList<>();

    public void addMyBook(Book book){
        MyBook myBook = MyBook.builder()
                .owner(this)
                .book(book)
                .build();
        myBooks.add(myBook);
    }

    public void removeMyBook(Book book){
        myBooks.removeIf(myBook -> myBook.getBook().equals(book));
    }

}