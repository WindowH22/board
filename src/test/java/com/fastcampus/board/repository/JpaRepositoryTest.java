package com.fastcampus.board.repository;

import com.fastcampus.board.config.JpaConfig;
import com.fastcampus.board.domain.Article;
import com.fastcampus.board.domain.UserAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;


import java.util.List;

import static org.assertj.core.api.Assertions.*;
@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class) // import 해주지 않으면 jpaauditing을 읽지 못한다.
@DataJpaTest
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    private final UserAccountRepository userAccountRepository;

    public JpaRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository,
            @Autowired UserAccountRepository userAccountRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
        this.userAccountRepository = userAccountRepository;
    }
    
    @Test
    void givenTestData_whenSelecting_thenWorkFine() {
        //Given

        //When
        List<Article> articles = articleRepository.findAll();
        //Then

        assertThat(articles)
                .isNotNull()
                .hasSize(123);
    }

    @DisplayName("select테스트")
    @Test
    void giveTestData_whenSelecting_thenWorksFine() {
        //Given

        //When
        List<Article> articles = articleRepository.findAll();
        //Then
        assertThat(articles)
                .hasSize(123);
    }

    @DisplayName("insert테스트")
    @Test
    void giveTestData_whenInserting_thenWorksFine(){
        //Given
        long previousCount =  articleRepository.count();
        UserAccount userAccount = userAccountRepository.save(UserAccount.of("newUno", "pw", null, null, null));
        //When
        Article savedArticle = articleRepository.save(Article.of(userAccount,"new content","spring","hashtag"));
        //Then
        assertThat(articleRepository.count()).isEqualTo(previousCount+1);
    }
    @DisplayName("update테스트")
    @Test
    void giveTestData_whenUpdating_thenWorksFine(){
        //Given
        Article article = articleRepository.findById(1L).orElseThrow();
        String updatedHashtag = "#springBoot";
        article.setHashtag(updatedHashtag);
        //When

        Article savedArticle = articleRepository.saveAndFlush(article);
        /**
         * saveAndFlush를 사용하는 이유
         * @DataJpaTest로 인하여 자동적으로 트랜잭션이 걸리고 이로 인해 rollback이 되면서 업데이트 문이 동작하지않음
         * 이로 인해 flush를 해줘야하는데 jpa에 등록되어있는 메서드중 saveAndFlush로 이를 처리 가능함
         * 다만 이는 롤백될 것이기에 반영되어지지는 않는다.
         */

        //Then
        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag",updatedHashtag);
    }

    @DisplayName("delete테스트")
    @Test
    void giveTestData_whenDelete_thenWorksFine(){
        //Given
        Article article = articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long previousArticleCommentCount = articleCommentRepository.count();
        int deletedCommentsSize = article.getArticleComments().size();
        //When
        articleRepository.delete(article);

        //Then
        assertThat(articleRepository.count()).isEqualTo(previousArticleCount-1);
        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount-deletedCommentsSize);
    }
}