package com.junho.Kopmorning.DTO;

import com.junho.Kopmorning.Domain.Member;
import com.junho.Kopmorning.Domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRequestDto {
    private String email;
    private String password;
    private String nickname;

    public Member toMember(PasswordEncoder passwordEncoder){
        return Member.builder()
                .email(email)
                .nickname(nickname)
                .password(passwordEncoder.encode(password))
                .role(Role.ROLE_USER)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication(){
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
