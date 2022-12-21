package com.fastcampus.board.dto;

import java.time.LocalDateTime;

public class ArticleCommentDto {
    public ArticleCommentDto(LocalDateTime createdAt, String createdBy, String modifiedBy, String content) {
    }

    public static ArticleCommentDto of(LocalDateTime createdAt, String createdBy, String modifiedBy, String content) {
        return new ArticleCommentDto(createdAt, createdBy, modifiedBy, content);
    }


}
