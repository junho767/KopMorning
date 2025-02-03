package com.football.KopMorning.auth.member.service;

import com.football.KopMorning.auth.member.domain.Member;
import com.football.KopMorning.auth.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member join(String email, String password) {
        Member member = Member.builder()
                .email(email)
                .password(password)
                .build();
        return memberRepository.save(member);
    }
}
