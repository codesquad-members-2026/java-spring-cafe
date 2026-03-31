package com.codesquad.cafe.domain;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private User writer;

    @Column(length = 1000)
    private String contents;

    private LocalDateTime createDate;
    private boolean deleted = false;

    protected Reply() {}

    public Reply(Article article, User writer, String contents) {
        this.article = article;
        this.writer = writer;
        this.contents = contents;
        this.createDate = LocalDateTime.now();
        this.deleted = false;
    }

    public Long getId() {
        return id;
    }

    public User getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }
}
