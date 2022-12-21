package com.fastcampus.board.dto;

import java.time.LocalDateTime;

public class ArticleCommentUpdateDto {
    public ArticleCommentUpdateDto(String content) {
    }

    public static ArticleCommentUpdateDto of(String content) {
        return new ArticleCommentUpdateDto(content);
    }


}
