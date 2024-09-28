package com.junho.Kopmorning.DTO;

import com.junho.Kopmorning.Domain.Article;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
public class PageResponseDTO {
    private Long articleId;
    private String articleTitle;
    private String memberNickname;
    private String createdAt;

    public static PageResponseDTO of(Article article){
        return PageResponseDTO.builder()
                .articleId(article.getId())
                .articleTitle(article.getTitle())
                .createdAt(article.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .memberNickname(article.getMember().getNickname())
                .build();
    }
}
