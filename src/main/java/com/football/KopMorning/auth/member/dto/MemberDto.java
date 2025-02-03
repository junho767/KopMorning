package com.football.KopMorning.auth.member.dto;

import com.football.KopMorning.auth.member.domain.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberDto {
    private long id;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.createdAt = member.getCreatedDate();
        this.lastLoginAt = member.getLastLoginDate();
    }
}
