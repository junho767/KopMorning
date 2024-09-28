package com.junho.Kopmorning.Controller;

import com.junho.Kopmorning.DTO.CreateRecommendDTO;
import com.junho.Kopmorning.DTO.MessageDTO;
import com.junho.Kopmorning.DTO.RecommendDTO;
import com.junho.Kopmorning.Service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recommend")
@RequiredArgsConstructor
public class RecommendController {
    private final RecommendService recommendService;

    @GetMapping("/list")
    public ResponseEntity<RecommendDTO> RecommendList(@RequestParam(name = "id") Long id){
       return ResponseEntity.ok(recommendService.allRecommend(id));
    }

    @PostMapping("/")
    public ResponseEntity<MessageDTO> PostRecommend(@RequestBody CreateRecommendDTO recommendDTO){
        recommendService.createRecommend(recommendDTO.getId());
        return ResponseEntity.ok(new MessageDTO("좋아요!"));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<MessageDTO> DeleteRecommend(@RequestParam(name = "id") Long id){
        recommendService.deleteRecommend(id);
        return ResponseEntity.ok(new MessageDTO("좋아요 취소"));
    }
}
