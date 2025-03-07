package com.ll.bookstore.domain.cash.cash.entity;


import com.ll.bookstore.domain.member.member.entity.Member;
import com.ll.bookstore.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jdk.jfr.EventType;
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
public class CashLog extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private EventType eventType;
    private String relTypeCode;
    private Long relId;
    @ManyToOne
    private Member member;
    private long price;

    public enum EventType{
        충전__무통장입금,
        충전__토스페이먼츠,
        출금__통장입금,
        사용__토스페이먼츠_주문결제,
        사용__예치금_주문결제,
        환불__예치금_주문결제,
        작가정산__예치금;
    }
}
