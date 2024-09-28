package com.junho.Kopmorning.Controller;

import com.junho.Kopmorning.DTO.CommentRequestDTO;
import com.junho.Kopmorning.DTO.CommentResponseDTO;
import com.junho.Kopmorning.DTO.MessageDTO;
import com.junho.Kopmorning.Service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/list")
    public ResponseEntity<List<CommentResponseDTO>> getComment(@RequestParam(name = "id") Long id){
        return ResponseEntity.ok(commentService.getComment(id));
    }

    @PostMapping("/")
    public ResponseEntity<CommentResponseDTO> createComment(@RequestBody CommentRequestDTO requestDTO){
        return ResponseEntity.ok(commentService.createComment(requestDTO.getArticle_id(),requestDTO.getBody()));
    }

    @DeleteMapping("/one")
    public ResponseEntity<MessageDTO> delete(@RequestParam(name = "id") Long id){
        commentService.deleteComment(id);
        return ResponseEntity.ok(new MessageDTO("삭제 완료!"));
    }
}
