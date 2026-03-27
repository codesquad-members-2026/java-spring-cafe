package com.codesquad.cafe.question;

public class QuestionSummary {
    public Long id;
    public String title;
    public String author;

    private QuestionSummary(Long id,String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public static QuestionSummary from(Question question) {
        return new QuestionSummary(question.getId(), question.getTitle(), question.getAuthor().getName());
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

}
