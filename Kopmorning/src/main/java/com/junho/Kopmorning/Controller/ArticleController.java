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

    // 수정하기 위해, 이전에 쓰여졌던 데이터를 불러오는 컨트롤러이다.
    @GetMapping("/change")
    public ResponseEntity<ArticleResponseDTO> getChangeArticle(@RequestParam(name = "id") Long id){
        return ResponseEntity.ok(articleService.getArticle(id));
    }
    // 수정
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
