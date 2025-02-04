package com.football.KopMorning.auth.member.dto;

import com.football.KopMorning.auth.member.domain.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberDto {
    private final long id;
    private final LocalDateTime createdAt;
    private final LocalDateTime lastLoginAt;
    private final String nickName;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.nickName = member.getNickName();
        this.createdAt = member.getCreatedDate();
        this.lastLoginAt = member.getLastLoginDate();
    }
}
