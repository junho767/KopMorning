package com.junho.Kopmorning.DTO;

import com.junho.Kopmorning.Domain.Article;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
public class ArticleResponseDTO {
    private Long articleId;
    private String memberNickname;
    private String articleTitle;
    private String articleContent;
    private String createdAt;
    private String updatedAt;
    private boolean isWritten;

    public static ArticleResponseDTO of(Article article, boolean bool){
        return ArticleResponseDTO.builder()
                .articleId(article.getId())
                .memberNickname(article.getMember().getNickname())
                .articleTitle(article.getTitle())
                .articleContent(article.getContent())
                .createdAt(article.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .updatedAt(article.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .isWritten(bool)
                .build();
    }
}
