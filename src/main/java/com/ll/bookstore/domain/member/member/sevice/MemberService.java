package com.ll.bookstore.domain.member.member.sevice;

import com.ll.bookstore.domain.base.genFile.GenFile;
import com.ll.bookstore.domain.base.genFile.service.GenFileService;
import com.ll.bookstore.domain.cash.cash.entity.CashLog;
import com.ll.bookstore.domain.cash.cash.service.CashService;
import com.ll.bookstore.domain.member.member.entity.Member;
import com.ll.bookstore.domain.member.member.repository.MemberRepository;
import com.ll.bookstore.global.app.AppConfig;
import com.ll.bookstore.global.jpa.BaseEntity;
import com.ll.bookstore.global.rsData.RsData;
import com.ll.bookstore.standard.utill.Ut.Ut;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CashService cashService;
    private final GenFileService genFileService;

    @Transactional
    public RsData<Member> join(String username, String password, String nickname) {
        return join(username, password, nickname, null);
    }

    @Transactional
    public RsData<Member> join(String username, String password, String nickname, String profileImgFilePath) {
        if (findByUsername(username).isPresent()) {
            return RsData.of("400-2", "이미 존재하는 회원입니다");
        }

        Member member = Member.builder().
                username(username)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .build();

        memberRepository.save(member);

        if (Ut.str.hasLength(profileImgFilePath)) {
            saveProfileImg(member, profileImgFilePath);
        }

        return RsData.of("200", "%s님 환영합니다. 회원가입이 완료되었습니다. 로그인 후 이용해주세요.".formatted(member.getUsername()), member);
    }

    private void saveProfileImg(Member member, String profileImgFilePath) {
        genFileService.save(member.getUsername(), member.getId(), "common", "profileImg", 1, profileImgFilePath);
    }

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    public void addCash(Member member, long price, CashLog.EventType eventType, BaseEntity relEntity) {
        CashLog cashLog = cashService.addCash(member, price, eventType, relEntity);

        long newRestCash = member.getRestCash() + cashLog.getPrice();
        member.setRestCash(newRestCash);
    }

    @Transactional
    public RsData<Member> whenSocialLogin(String providerTypeCode, String username, String nickname, String profileImgUrl) {
        Optional<Member> opMember = findByUsername(username);

        if (opMember.isPresent()) return RsData.of("200", "이미 존재합니다.", opMember.get());

        String filePath = Ut.str.hasLength(profileImgUrl) ? Ut.file.downloadFileByHttp(profileImgUrl, AppConfig.getTempDirPath()) : "";

        return join(username, "", nickname, filePath);
    }

    public String getProfileImgUrl(Member member) {
        return Optional.ofNullable(member)
                .flatMap(this::findProfileImgUrl)
                .orElse("https://placehold.co/30x30?text=UU");
    }
    private Optional<String> findProfileImgUrl(Member member) {
        return genFileService.findBy(
                        member.getModelName(), member.getId(), "common", "profileImg", 1
                )
                .map(GenFile::getUrl);
    }

    public RsData<Member> join(String username, String password, String nickname, MultipartFile profileImg) {
        String profileImgFilePath = Ut.file.toFile(profileImg, AppConfig.getTempDirPath());
        return join(username, password, nickname, profileImg);
    }
}
