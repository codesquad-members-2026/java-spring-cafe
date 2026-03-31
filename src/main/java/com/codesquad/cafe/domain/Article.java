package com.codesquad.cafe.domain;
import jakarta.persistence.*;

import java.util.List;


@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private User writer;
    private String title;

    @Column(length = 1000)
    private String contents;


    @OneToMany(mappedBy = "article")
    @OrderBy("id DESC")
    private List<Reply> replies;

    public List<Reply> getReplies() {
        return replies;
    }

    public User getWriter() {
        return writer;
    }
    public void setWriter(User writer) {
        this.writer = writer;
    }


    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }


    public String getContents() {
        return contents;
    }
    public void setContents(String contents) {
        this.contents = contents;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {this.id = id; }
}
