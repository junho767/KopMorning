package com.junho.Kopmorning.DTO;

import com.junho.Kopmorning.Domain.Article;
import com.junho.Kopmorning.Domain.Comment;
import com.junho.Kopmorning.Domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Builder
@Setter
public class CommentResponseDTO {
    private Long commentId;
    private String memberNickName;
    private String body;
    private Long createdAt;
    private boolean isWritten;

    public static CommentResponseDTO of(Comment comment, boolean isWritten){
        return CommentResponseDTO.builder()
                .commentId(comment.getId())
                .body(comment.getText())
                .createdAt(Timestamp.valueOf(comment.getCreateAt()).getTime())
                .isWritten(isWritten)
                .memberNickName(comment.getMember().getNickname())
                .build();
    }
}
