package com.junho.Kopmorning.Controller;

import com.junho.Kopmorning.DTO.MemberRequestDto;
import com.junho.Kopmorning.DTO.MemberResponseDto;
import com.junho.Kopmorning.DTO.TokenDto;
import com.junho.Kopmorning.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto requestDto){
        return ResponseEntity.ok(authService.login(requestDto));
    }
    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto requestDto){
        return ResponseEntity.ok(authService.signUp(requestDto));
    }
}
