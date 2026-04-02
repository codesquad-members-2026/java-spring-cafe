package com.codesquad.cafe.qna;

import com.codesquad.cafe.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contents;
    private LocalDateTime createdTime;

    @ManyToOne
    @JoinColumn(name = "writer")
    private User writer;

    @ManyToOne
    @JoinColumn(name = "article")
    private Article article;

    private Comment(){}
    
    // TODO: 왜 엔터티로 DB에 저장할 때는 LocalDateTime 형식에 저장해야 하는가?: 나중에 시간 객체여야 3일 전 글 보기, 최신순 정렬 등으로 변주를 줄 수 있어서
    public Comment(String contents, LocalDateTime createdTime, User writer, Article article) {
        this.contents = contents;
        this.createdTime = createdTime;
        this.writer = writer;
        this.article = article;
    }

    public Long getId() {
        return id;
    }
    public String getContents() {
        return contents;
    }
    public LocalDateTime getCreatedTime() {
        return createdTime;
    }
    public User getWriter() {
        return writer;
    }
    public Article getArticle() {
        return article;
    }

    // TODO: 객체 비교 더 공부
    public boolean isBelongToWriter(User target) {
        if(this.writer == null || target == null)
            return false;

        return this.writer.getId().equals(target.getId());
    }

    public boolean isInArticle(Long articleId) {
        return this.article.getId().equals(articleId);
    }
}