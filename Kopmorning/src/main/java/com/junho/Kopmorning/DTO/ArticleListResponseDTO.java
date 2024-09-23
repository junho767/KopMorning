package com.junho.Kopmorning.DTO;

import com.junho.Kopmorning.Domain.Article;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
public class ArticleListResponseDTO {
    private Long articleId;
    private String articleTitle;
    private String memberNickname;
    private String createdAt;


    public static ArticleListResponseDTO of(Article article) {
        return ArticleListResponseDTO.builder()
                .articleId(article.getId())
                .articleTitle(article.getTitle())
                .memberNickname(article.getMember().getNickname())
                .createdAt(article.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }
}
