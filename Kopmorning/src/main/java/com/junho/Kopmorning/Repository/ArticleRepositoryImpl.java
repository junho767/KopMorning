package com.junho.Kopmorning.Repository;

import com.junho.Kopmorning.DTO.PageResponseDTO;
import com.junho.Kopmorning.Domain.Article;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.junho.Kopmorning.Domain.QArticle.article;

@Repository
@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PageResponseDTO> searchAll(Pageable pageable) {
        List<Article> articleList = queryFactory
                .selectFrom(article)
                .orderBy(article.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<PageResponseDTO> pages = articleList
                .stream()
                .map(PageResponseDTO::of)
                .toList();

        int totalSize = queryFactory
                .selectFrom(article)
                .fetch()
                .size();

        return new PageImpl<>(pages, pageable, totalSize);
    }
}
