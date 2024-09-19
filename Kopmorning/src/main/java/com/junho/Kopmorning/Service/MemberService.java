package com.junho.Kopmorning.Service;

import com.junho.Kopmorning.Config.SecurityUtil;
import com.junho.Kopmorning.DTO.MemberResponseDto;
import com.junho.Kopmorning.Domain.Member;
import com.junho.Kopmorning.Repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberResponseDto getMyInfoBySecurity(){
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new RuntimeException("등록된 사용자가 아닙니다."));
    }

    @Transactional
    public MemberResponseDto changeMemberNickname(String nickname){
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(()->new RuntimeException("올바른 접근이 아닙니다."));
        member.setNickname(nickname);
        return MemberResponseDto.of(memberRepository.save(member));
    }

    @Transactional
    public MemberResponseDto changeMemberPassword(String prePassword, String newPassword){
        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(()-> new RuntimeException("Error"));
        if(!passwordEncoder.matches(prePassword, member.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        member.setPassword(newPassword);
        return MemberResponseDto.of(memberRepository.save(member));
    }
}
