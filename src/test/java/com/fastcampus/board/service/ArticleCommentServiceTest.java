package com.fastcampus.board.service;

import com.fastcampus.board.config.SecurityConfig;
import com.fastcampus.board.domain.Article;
import com.fastcampus.board.domain.ArticleComment;
import com.fastcampus.board.dto.ArticleCommentDto;
import com.fastcampus.board.dto.ArticleCommentUpdateDto;
import com.fastcampus.board.dto.ArticleUpdateDto;
import com.fastcampus.board.repository.ArticleCommentRepository;
import com.fastcampus.board.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
@Import(SecurityConfig.class)
@DisplayName("비즈니스 로직 - 댓글")
@ExtendWith(MockitoExtension.class)
class ArticleCommentServiceTest {

    @InjectMocks private ArticleCommentService sut;

    @Mock private ArticleRepository articleRepository;
    @Mock private ArticleCommentRepository articleCommentRepository;

    @DisplayName("게시글 ID로 조회하면, 해당하는 댓글들을 불러온다.")
    @Test
    void givenArticleId_whenSearchingComments_thenReturnsComments(){
        //Given
        Long articleId = 1L;

        given(articleRepository.findById(articleId)).willReturn(Optional.of(
                Article.of("title","content","#java"))
        );

        //When
        List<ArticleCommentDto> articleComments = sut.searchArticleComment(articleId);

        //Then
        assertThat(articleComments).isNotNull();
    }

    @DisplayName("댓글 정보를 입력하면, 해당하는 댓글을 저장한다.")
    @Test
    void givenArticleCommentsInfo_whenSavingArticleComments_thenArticleComments(){
        //Given
        ArticleCommentDto dto = ArticleCommentDto.of(LocalDateTime.now(),"chang","chang","comment");

        given(articleCommentRepository.save(any(ArticleComment.class))).willReturn(null);

        //When
        sut.saveArticleComment(dto);

        //Then
        then(articleCommentRepository).should().save(any(ArticleComment.class));
    }

    @DisplayName("댓글 ID와 수정 정보를 입력하면, 댓글을 수정한다.")
    @Test
    void givenArticleCommentIdAndModifiedInfo_whenUpdatingArticleComment_thenUpdateArticleComment(){
        //Given
        given(articleCommentRepository.save(any(ArticleComment.class))).willReturn(null);

        //When
        sut.updateArticleComment(1L, ArticleCommentUpdateDto.of("content"));

        //Then
        then(articleCommentRepository).should().save(any(ArticleComment.class));
    }

    @DisplayName("댓글ID를 입력하면, 댓글을 삭제한다.")
    @Test
    void givenArticleCommentId_whenDeletingArticleComment_thenDeleteArticleComment(){
        //Given
        willDoNothing().given(articleCommentRepository).delete(any(ArticleComment.class));

        //When
        sut.deleteArticleComments(1L);

        //Then
        then(articleCommentRepository).should().delete(any(ArticleComment.class));
    }
}