package com.ll.bookstore.domain.product.order.controller;

import com.ll.bookstore.domain.member.member.entity.Member;
import com.ll.bookstore.domain.product.order.entity.Order;
import com.ll.bookstore.domain.product.order.service.OrderService;
import com.ll.bookstore.global.exceptions.GlobalException;
import com.ll.bookstore.global.rq.Rq;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final Rq rq;
    private final OrderService orderService;

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public String showDetail(@PathVariable long id, Model model) {
        Order order = orderService.findById(id).orElse(null);
        if (order == null) {
            throw new GlobalException("404", "존재하지 않는 주문입니다.");
        }

        Member actor = rq.getMember();
        long restCash = actor.getRestCash();

        if (!orderService.actorCanSee(actor, order)) {
            throw new GlobalException("403", "권한이 없습니다.");
        }

        model.addAttribute("order", order);
        model.addAttribute("actorRestCash", restCash);

        return "domain/product/order/detail";
    }

}
