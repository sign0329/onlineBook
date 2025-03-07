package com.ll.bookstore.global.security;

import com.ll.bookstore.domain.member.member.entity.Member;
import com.ll.bookstore.domain.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomUserDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> opMember = memberRepository.findByUsername(username);

        if(opMember.isEmpty()){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        Member member = opMember.get();

        return new SecurityUser(
                member.getId(),
                member.getUsername(),
                member.getPassword(),
                member.getAuthorities()
        );
    }
}
