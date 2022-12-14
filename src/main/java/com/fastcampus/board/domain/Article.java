package com.fastcampus.board.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
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
@Entity
public class Article extends AuditingFields{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) //mysql과 같은방식 ->AUTO로 설정 시 에러 남
    private Long id;

    // setter를 따로 주는 이유는 자동으로 주는 값에 대해 인위적인 변경을 막기위함
    @Setter @Column(nullable = false)
    private String title; // 제목
    @Setter
    @JoinColumn(name = "userId")
    @ManyToOne(optional = false)
    private UserAccount userAccount; // 유저 정보 (ID)
    @Setter @Column(nullable = false, length = 10000)
    private String content; // 내용
    @Setter private String hashtag; // 해시태그

    @ToString.Exclude // exclude를 통해 tostring을 끊는다.하지않으면 순환참조가 발생한다.
    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    protected Article(){}

    private Article(UserAccount userAccount, String title, String content,String hashtag) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of(UserAccount userAccount, String title, String content,String hashtag) {
        return new Article(userAccount, title, content,hashtag);
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
