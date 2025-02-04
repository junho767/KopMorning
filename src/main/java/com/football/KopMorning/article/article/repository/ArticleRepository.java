package com.football.KopMorning.article.article.repository;

import com.football.KopMorning.article.article.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,Long> {
}
