package com.junho.Kopmorning.Service;

import com.junho.Kopmorning.DTO.MemberRequestDto;
import com.junho.Kopmorning.DTO.MemberResponseDto;
import com.junho.Kopmorning.DTO.TokenDto;
import com.junho.Kopmorning.Domain.Member;
import com.junho.Kopmorning.Repository.MemberRepository;
import com.junho.Kopmorning.jwt.TokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public MemberResponseDto signUp(MemberRequestDto memberRequestDto){
        if(memberRepository.existsByEmail(memberRequestDto.getEmail())){
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }
        Member member = memberRequestDto.toMember(passwordEncoder);
        return  MemberResponseDto.of(memberRepository.save(member));
    }

    public TokenDto login(MemberRequestDto requestDto){
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return tokenProvider.generateTokenDto(authentication);
    }
}
