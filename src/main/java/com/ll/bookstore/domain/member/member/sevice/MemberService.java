package com.ll.bookstore.domain.member.member.sevice;

import com.ll.bookstore.domain.cash.cash.entity.CashLog;
import com.ll.bookstore.domain.cash.cash.service.CashService;
import com.ll.bookstore.domain.member.member.entity.Member;
import com.ll.bookstore.domain.member.member.repository.MemberRepository;
import com.ll.bookstore.global.jpa.BaseEntity;
import com.ll.bookstore.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CashService cashService;

    @Transactional
    public RsData<Member> join(String username, String password) {
        if (findByUsername(username).isPresent()) {
            return RsData.of("400-2", "이미 존재하는 회원입니다");
        }

        Member member = Member.builder().
                username(username)
                .password(passwordEncoder.encode(password))
                .build();

        memberRepository.save(member);

        return RsData.of("200", "%s님 환영합니다. 회원가입이 완료되었습니다. 로그인 후 이용해주세요.".formatted(member.getUsername()), member);
    }

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    public void addCash(Member member, long price, CashLog.EventType eventType, BaseEntity relEntity) {
        CashLog cashLog = cashService.addCash(member, price, eventType, relEntity);

        long newRestCash = member.getRestCash() + cashLog.getPrice();
        member.setRestCash(newRestCash);
    }


}
