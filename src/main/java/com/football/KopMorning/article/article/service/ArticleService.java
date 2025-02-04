package com.football.KopMorning.article.article.service;

import com.football.KopMorning.article.article.domain.Article;
import com.football.KopMorning.article.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public Article save(String title, String content) {
        Article article = Article.builder()
                .title(title)
                .content(content)
                .build();

        return articleRepository.save(article);
    }
}
