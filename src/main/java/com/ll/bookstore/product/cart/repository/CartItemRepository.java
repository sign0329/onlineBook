package com.ll.bookstore.product.cart.repository;

import com.ll.bookstore.product.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
