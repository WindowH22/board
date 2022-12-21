package com.fastcampus.board.dto;


public class ArticleUpdateDto {
    public ArticleUpdateDto(String title, String content, String hashtag) {
    }

    public static ArticleUpdateDto of(String title, String content, String hashtag) {
        return new ArticleUpdateDto(title, content, hashtag);
    }


}
