package com.junho.Kopmorning.Service;


import com.junho.Kopmorning.DTO.ArticleListResponseDTO;
import com.junho.Kopmorning.Domain.Article;
import com.junho.Kopmorning.Repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
// 해당 메서드나 클래스가 데이터베이스 트랜잭션을 읽기 전용으로 처리함을 나타냅니다
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public List<ArticleListResponseDTO> findAll(){
        List<Article> articles = articleRepository.findAll();
        return articles.stream().map(ArticleListResponseDTO::of).collect(Collectors.toList());
    }
}
