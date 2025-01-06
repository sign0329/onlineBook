package com.ll.bookstore.domain.product.cart.repository;

import com.ll.bookstore.domain.member.member.entity.Member;
import com.ll.bookstore.domain.product.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByBuyer(Member buyer);

}
