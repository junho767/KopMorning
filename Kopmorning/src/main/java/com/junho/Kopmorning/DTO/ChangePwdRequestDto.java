package com.junho.Kopmorning.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePwdRequestDto {
    private String email;
    private String prePassword;
    private String newPassword;
}
