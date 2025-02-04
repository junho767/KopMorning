package com.football.KopMorning.article.article.dto;

import com.football.KopMorning.article.article.domain.Article;
import com.football.KopMorning.auth.member.domain.Member;
import lombok.Getter;

@Getter
public class ArticleDto {
    private Long id;
    private String title;
    private String content;
    private Member member;

    public ArticleDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
