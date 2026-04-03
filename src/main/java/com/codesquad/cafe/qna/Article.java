package com.codesquad.cafe.qna;

import com.codesquad.cafe.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@SQLRestriction("is_activated = true")
@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer")
    private User writer;

    private String title;
    private String contents;

    @Column(name = "is_activated")
    private Boolean isActive;

    // TODO: 어떤 원리로 작동하지
    @OneToMany(mappedBy = "article")
    private List<Comment> comments = new ArrayList<>();

    protected Article() {}

    public Article(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.isActive = true;
    }

    public Long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getContents() {
        return contents;
    }
    public User getWriter() {return writer;}

    public void updateTitleAndContents(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
    public boolean isWrittenBy(User sessionUser){
        return this.getWriter().getId().equals(sessionUser.getId());
    }
    public boolean hasOtherUsersComments(User sessionUser) {
        return comments.stream()
                .anyMatch(comment -> !comment.getWriter().getId().equals(sessionUser.getId()));
    }
    public void switchToDeletedState() {
        this.isActive = false;
    }
}
