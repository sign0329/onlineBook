package com.ll.bookstore.domain.product.order.service;


import com.ll.bookstore.domain.member.member.entity.Member;
import com.ll.bookstore.domain.product.cart.entity.CartItem;
import com.ll.bookstore.domain.product.cart.service.CartService;
import com.ll.bookstore.domain.product.order.entity.Order;
import com.ll.bookstore.domain.product.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;

    @Transactional
    public Order createFromCart(Member buyer) {
        List<CartItem> cartItems = cartService.findItemsByBuyer(buyer);
        Order order = Order.builder()
                .buyer(buyer)
                .build();
        cartItems.stream()
                .forEach(order::addItem);
        orderRepository.save(order);
        cartItems.stream()
                .forEach(cartService::delete);
        return order;
    }
}
