package com.junho.Kopmorning.Controller;

import com.junho.Kopmorning.DTO.ChangePwdRequestDto;
import com.junho.Kopmorning.DTO.MemberRequestDto;
import com.junho.Kopmorning.DTO.MemberResponseDto;
import com.junho.Kopmorning.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> getMyMemberInfo() {
        MemberResponseDto myInfoBySecurity = memberService.getMyInfoBySecurity();
        return ResponseEntity.ok((myInfoBySecurity));
        // return ResponseEntity.ok(memberService.getMyInfoBySecurity());
    }

    @PostMapping("/nickname")
    public ResponseEntity<MemberResponseDto> setMemberNickname(@RequestBody MemberRequestDto request) {
        return ResponseEntity.ok(memberService.changeMemberNickname(request.getNickname()));
    }

    @PostMapping("/password")
    public ResponseEntity<MemberResponseDto> setMemberPassword(@RequestBody ChangePwdRequestDto requestDto) {
        return ResponseEntity.ok(memberService.changeMemberPassword(requestDto.getPrePassword(), requestDto.getNewPassword()));
    }
}
