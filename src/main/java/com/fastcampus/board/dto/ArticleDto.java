package com.fastcampus.board.dto;

import com.fastcampus.board.domain.Article;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ArticleDto{
    public ArticleDto(LocalDateTime createdAt, String createdBy, String title, String content, String hashtag) {
    }

    public static ArticleDto of(LocalDateTime createdAt, String createdBy, String title, String content, String hashtag) {
        return new ArticleDto(createdAt, createdBy, title, content, hashtag);
    }


}
