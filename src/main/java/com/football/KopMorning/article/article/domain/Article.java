package com.football.KopMorning.article.article.domain;

import com.football.KopMorning.auth.member.domain.Member;
import com.football.KopMorning.global.dto.BaseTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Article extends BaseTime {
    @Column(length = 20, nullable = false)
    private String title;
    @Column(length = 500)
    private String content;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "member_id")
    private Member member;
}
