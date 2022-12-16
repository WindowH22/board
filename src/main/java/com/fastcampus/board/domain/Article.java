package com.fastcampus.board.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Article {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) //mysql과 같은방식 ->AUTO로 설정 시 에러 남
    private Long id;

    // setter를 따로 주는 이유는 자동으로 주는 값에 대해 인위적인 변경을 막기위함
    @Setter @Column(nullable = false)
    private String title; // 제목
    @Setter @Column(nullable = false, length = 10000)
    private String content; // 내용
    @Setter private String hashtag; // 해시태그

    @ToString.Exclude // exclude를 통해 tostring을 끊는다.하지않으면 순환참조가 발생한다.
    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();


    @CreatedDate @Column(nullable = false)
    private LocalDateTime createdAt; // 생성일시
    @CreatedBy @Column(nullable = false, length = 100)
    private String createdBy; //생성자
    @LastModifiedDate @Column(nullable = false)
    private LocalDateTime modifiedAt; // 수정일시
    @LastModifiedBy @Column(nullable = false, length = 100)
    private String modifiedBy; // 수정자

    protected Article(){}

    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of(String title, String content, String hashtag){
        return new Article(title,content,hashtag);
    }

    //list로 데이터 받을 때 비교성 검사 (id가 유니크 값이기 때문에 id값사용

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return id != null && id.equals(article.id);  //id 가 부여되지 않았다면 영속화 되지않았기 떄문에 동등성 검사가 의미가 없음
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
