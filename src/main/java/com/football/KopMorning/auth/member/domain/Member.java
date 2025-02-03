package com.football.KopMorning.auth.member.domain;

import com.football.KopMorning.global.dto.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
public class Member extends BaseTime {

    @Column(length = 100, unique = true)
    private String email;
    @Column(length = 100, unique = true)
    private String password;
    @Column(length = 10, unique = true)
    private String nickName;

}
