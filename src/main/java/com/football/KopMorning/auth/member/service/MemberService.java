package com.football.KopMorning.auth.member.service;

import com.football.KopMorning.auth.member.domain.Member;
import com.football.KopMorning.auth.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final RandNicknameService randNicknameService;
    private final PasswordEncoder passwordEncoder;

    public Member join(String email, String password) {
        String randNickname = randNicknameService.randNickname();

        Member member = Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickName(randNickname)
                .build();

        return memberRepository.save(member);
    }
}
