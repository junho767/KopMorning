package com.junho.Kopmorning.Config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

// SecurityContext 에 유저 정보가 저장되는 시점의 클래스이다.
// 요청이 들어오면 JwtFilter 의 doFilter 에 저장 되는데 거기에 있는 인증 정보를 꺼내 멤버의 ID를 반환함.
public class SecurityUtil {
    private SecurityUtil() { }

    public static Long getCurrentMemberId(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || authentication.getName() == null){
            throw new RuntimeException("인증 정보가 존재하지 않습니다.");
        }

        return Long.parseLong(authentication.getName());
    }
}
