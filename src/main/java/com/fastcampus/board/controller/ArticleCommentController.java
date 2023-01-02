package com.fastcampus.board.controller;

import com.fastcampus.board.dto.UserAccountDto;
import com.fastcampus.board.dto.request.ArticleCommentRequest;
import com.fastcampus.board.dto.security.BoardPrincipal;
import com.fastcampus.board.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;

    @PostMapping ("/new")
    public String postNewArticleComment(ArticleCommentRequest articleCommentRequest,
                                        @AuthenticationPrincipal BoardPrincipal boardPrincipal
    ) {

        articleCommentService.saveArticleComment(articleCommentRequest.toDto(boardPrincipal.toDto()));
        return "redirect:/articles/" + articleCommentRequest.articleId();
    }

    @PostMapping("/{commentId}/delete")
    public String deleteArticle(@PathVariable Long commentId,
                                @AuthenticationPrincipal BoardPrincipal boardPrincipal,
                                Long articleId

    ){
        articleCommentService.deleteArticleComment(commentId,boardPrincipal.getUsername());
        return "redirect:/articles/"+articleId;
    }
}
