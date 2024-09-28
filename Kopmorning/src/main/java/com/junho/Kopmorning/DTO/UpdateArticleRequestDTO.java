package com.junho.Kopmorning.DTO;

import lombok.Getter;

@Getter
public class UpdateArticleRequestDTO {
    private Long id;
    private String title;
    private String content;
}
