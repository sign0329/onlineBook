package com.ll.bookstore.domain.product.order.service;


import com.ll.bookstore.domain.cash.cash.entity.CashLog;
import com.ll.bookstore.domain.member.member.entity.Member;
import com.ll.bookstore.domain.member.member.sevice.MemberService;
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
    private final MemberService memberService;

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

    public void payByCashOnly(Order order){
        Member buyer = order.getBuyer();
        long restCash = buyer.getRestCash();
        long payPrice = order.calcPayPrice();

        if (payPrice > restCash){
            throw new RuntimeException("예치금이 부족합ㄴ디ㅏ.");
        }

        memberService.addCash(buyer, payPrice * -1, CashLog.EventType.환불__예치금_주문결제, order);

        payDone(order);
    }

    private void payDone(Order order){
        order.setPaymetDone();
    }
}
