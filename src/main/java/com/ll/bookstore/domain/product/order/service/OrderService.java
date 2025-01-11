package com.ll.bookstore.domain.product.order.service;


import com.ll.bookstore.domain.cash.cash.entity.CashLog;
import com.ll.bookstore.domain.member.member.entity.Member;
import com.ll.bookstore.domain.member.member.sevice.MemberService;
import com.ll.bookstore.domain.product.cart.entity.CartItem;
import com.ll.bookstore.domain.product.cart.service.CartService;
import com.ll.bookstore.domain.product.order.entity.Order;
import com.ll.bookstore.domain.product.order.repository.OrderRepository;
import com.ll.bookstore.global.exceptions.GlobalException;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
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

    @Transactional
    public void payByCashOnly(Order order){
        Member buyer = order.getBuyer();
        long restCash = buyer.getRestCash();
        long payPrice = order.calcPayPrice();

        if (payPrice > restCash){
            throw new GlobalException("400-1","예치금이 부족합니다.");
        }

        memberService.addCash(buyer, payPrice * -1, CashLog.EventType.환불__예치금_주문결제, order);

        payDone(order);
    }

    private void payDone(Order order){
        order.setPaymentDone();
    }

    public void refund(Order order){
        long payPrice = order.calcPayPrice();

        memberService.addCash(order.getBuyer(), payPrice, CashLog.EventType.환불__예치금_주문결제,order);

        order.setCancelDone();
        order.setRefundDone();
    }

    public void checkCanPay(Order order, long pgPayPrice) {
    if(!canPay(order, pgPayPrice))throw new GlobalException("400-2", "PG결제금액 혹은 예치금이 부족하여 결제할수 없습니다.");
    }

    private boolean canPay(Order order, long pgPayPrice) {
        long restCash = order.getBuyer().getRestCash();

        return order.calcPayPrice() <=restCash + pgPayPrice;
    }

    public void payByTossPayments(Order order, long pgPayPrice){
        Member buyer = order.getBuyer();
        long restCash =  buyer.getRestCash();
        long payPrice =  order.calcPayPrice();

        long userRestCash = payPrice - pgPayPrice;

        memberService.addCash(buyer, pgPayPrice, CashLog.EventType.충전__토스페이먼츠, order);
        memberService.addCash(buyer, pgPayPrice * -1, CashLog.EventType.사용__토스페이먼츠_주문결제, order);

        if(userRestCash > 0){
            if(userRestCash > restCash){
                throw new RuntimeException("에치금이 부족합니다.");
            }
        }

    }


}
