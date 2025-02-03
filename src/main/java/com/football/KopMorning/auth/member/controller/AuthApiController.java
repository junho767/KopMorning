package com.football.KopMorning.auth.member.controller;

import com.football.KopMorning.auth.member.domain.Member;
import com.football.KopMorning.auth.member.dto.MemberDto;
import com.football.KopMorning.auth.member.service.MemberService;
import com.football.KopMorning.global.entity.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthApiController {
    private final MemberService memberService;
    record memberRequestDto(@NotBlank @Length(min = 3) String email,
                            @NotBlank @Length(min = 3)String password){}

    @PostMapping("/join")
    public RsData<MemberDto> signUp(@RequestBody @Valid memberRequestDto requestDto) {
        Member member = memberService.join(requestDto.email, requestDto.password);
        return new RsData<>(
                "200-1",
                "회원가입 성공",
                new MemberDto(member));
    }
}
