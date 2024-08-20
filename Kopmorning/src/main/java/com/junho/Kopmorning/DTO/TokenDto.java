package com.junho.Kopmorning.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {
    public String grantType;
    public String accessToken;
    public Long tokenExpiresIn;
}
