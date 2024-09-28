package com.junho.Kopmorning.DTO;

import com.junho.Kopmorning.Domain.Article;
import com.junho.Kopmorning.Domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentRequestDTO {
    private Long article_id;
    private String body;
}
