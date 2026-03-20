package com.codesquad.cafe.qna;

public class Article {
    private Integer id;
    private String title;
    private String contents;

    Article(Integer id, String title, String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
