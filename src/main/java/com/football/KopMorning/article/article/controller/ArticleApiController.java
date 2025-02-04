package com.football.KopMorning.article.article.controller;

import com.football.KopMorning.article.article.domain.Article;
import com.football.KopMorning.article.article.dto.ArticleDto;
import com.football.KopMorning.article.article.service.ArticleService;
import com.football.KopMorning.global.entity.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleApiController {
    private final ArticleService articleService;

    record articleRequestDto(@NotBlank String title,
                             String content) {}
    @PostMapping("/write")
    public RsData<ArticleDto> write(@RequestBody articleRequestDto requestDto) {
        Article article = articleService.save(requestDto.title, requestDto.content);
        return new RsData<>(
                "200-1",
                "글 작성에 성공하였습니다.",
                new ArticleDto(article)
        );
    }
}
