package com.junho.Kopmorning.Repository;

import com.junho.Kopmorning.Domain.Article;
import com.junho.Kopmorning.Domain.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {
    List<Recommend> findByArticle(Article article);
}
