package com.codesquad.cafe.qna;

import com.codesquad.cafe.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: LAZY를 왜 사용하는가
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer")
    private User writer;

    private String title;
    private String contents;

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
    public void setWriter(User writer) {
        this.writer = writer;
    } // TODO: 언젠간 없앨 것임
}
