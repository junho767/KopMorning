package com.football.KopMorning.auth.member.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandNicknameService {
    private static final String prefix = "유저_";
    private static final int length = 7;

    public String randNickname() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(prefix);

        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
