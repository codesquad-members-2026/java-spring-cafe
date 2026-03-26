package com.codesquad.article;

import com.codesquad.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "Articles")
public class Article {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_pk")
    private User user;
    private String author;
    private String title;
    private String content;

    public String getAuthor() {
        return author;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAuthor() {
        this.author = this.user.getId();
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
