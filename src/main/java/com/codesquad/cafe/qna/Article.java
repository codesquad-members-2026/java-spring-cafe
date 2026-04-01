package com.codesquad.cafe.qna;

import com.codesquad.cafe.user.User;
import jakarta.persistence.*;

import java.util.List;

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

    @OneToMany
    private List<Comment> comments;

    protected Article() {}

    public Article(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
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
    public List<Comment> getComments() {return comments;}

    public void updateTitleAndContents(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
    public boolean isWrittenBy(User sessionUser){
        return this.getWriter().getId().equals(sessionUser.getId());
    }
}
