package com.codesquad.cafe.qna;

import com.codesquad.cafe.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@SQLRestriction("is_activated = true")
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contents;
    private LocalDateTime createdTime;

    @Column(name = "is_activated")
    private boolean isActivated;

    @ManyToOne
    @JoinColumn(name = "writer")
    private User writer;

    @ManyToOne
    @JoinColumn(name = "article")
    private Article article;

    private Comment(){}
    
    public Comment(String contents, LocalDateTime createdTime, User writer, Article article) {
        this.contents = contents;
        this.createdTime = createdTime;
        this.writer = writer;
        this.article = article;
        this.isActivated = true;
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

    public boolean isBelongToWriter(User target) {
        if(this.writer == null || target == null)
            return false;

        return this.writer.getId().equals(target.getId());
    }
    public boolean isInArticle(Long articleId) {
        return this.article.getId().equals(articleId);
    }
    public void switchToDeletedState(){this.isActivated = false;}
}