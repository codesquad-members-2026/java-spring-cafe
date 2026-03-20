package com.codesquad.article;

public class Article {

    private int id;
    private final String title;
    private final String content;

    public Article(String title, String content){
        this.title = title;
        this.content=content;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
