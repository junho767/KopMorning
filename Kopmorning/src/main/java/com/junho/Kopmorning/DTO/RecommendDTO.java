package com.junho.Kopmorning.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecommendDTO {
    private int recommendCnt;
    private boolean isRecommend;

    public static RecommendDTO noRecommend(){
        return RecommendDTO.builder()
                .recommendCnt(0)
                .isRecommend(false)
                .build();
    }
}
