package com.junho.Kopmorning.Controller;

import com.junho.Kopmorning.DTO.*;
import com.junho.Kopmorning.Service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/page")
    public ResponseEntity<Page<PageResponseDTO>> getPage(@RequestParam(name = "page") int page){
        return ResponseEntity.ok(articleService.pageArticle(page));
    }

    @GetMapping("/one")
    public ResponseEntity<ArticleResponseDTO> getArticle(@RequestParam(name = "id") Long id){
        return ResponseEntity.ok(articleService.getArticle(id));
    }

    @PostMapping("/")
    public ResponseEntity<ArticleResponseDTO> postArticle(@RequestBody NewArticleRequestDTO requestDTO){
        return ResponseEntity.ok(articleService.postArticle(requestDTO.getTitle(), requestDTO.getContent()));
    }

    @GetMapping("/update")
    public ResponseEntity<ArticleResponseDTO> getUpdateArticle(@RequestParam(name = "id") Long id){
        return ResponseEntity.ok(articleService.getArticle(id));
    }
    @PutMapping("/")
    public ResponseEntity<ArticleResponseDTO> putUpdateArticle(@RequestBody UpdateArticleRequestDTO requestDTO){
        return ResponseEntity.ok(articleService.updateArticle(requestDTO.getId(), requestDTO.getTitle(), requestDTO.getContent()));
    }
    @DeleteMapping("/one")
    public ResponseEntity<MessageDTO> deleteArticle(@RequestParam(name = "id") Long id){
        articleService.deleteArticle(id);
        return ResponseEntity.ok(new MessageDTO("삭제 성공"));
    }
}
