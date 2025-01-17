package com.ll.bookstore.domain.member.member.controller;

import com.ll.bookstore.domain.member.member.entity.Member;
import com.ll.bookstore.domain.member.member.sevice.MemberService;
import com.ll.bookstore.global.rq.Rq;
import com.ll.bookstore.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final Rq rq;

    @GetMapping("/join")
    @PreAuthorize("isAnonymous()")
    public String showJoin(){
        return "domain/member/member/join";    }


    @Getter
    @Setter
    public static class JoinForm{
        @NotBlank
        private String username;
        @NotBlank
        private String password;
        @NotBlank
        private String nickname;
        private MultipartFile profileImg;

    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/join")
    public String join(@Valid JoinForm joinForm){
        RsData<Member> joinRs = memberService.join(joinForm.getUsername(),joinForm.getPassword(), joinForm.getNickname(), joinForm.getProfileImg());

        return rq.redirectOrBack(joinRs, "/member/login");
    }

    @GetMapping("/login")
    public String showLogin(){
        return "domain/member/member/login";
    }

}
