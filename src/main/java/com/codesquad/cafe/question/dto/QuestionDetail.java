package com.codesquad.cafe.question.dto;

import com.codesquad.cafe.question.Question;

public class QuestionDetail {
    public String title;
    public String contents;
    public String author;

    private QuestionDetail(String title, String contents, String author) {
        this.title = title;
        this.contents = contents;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getAuthor() {
        return author;
    }

    public static QuestionDetail from(Question question) {
        return new QuestionDetail(question.getTitle(),
                question.getContents(), question.getAuthor().getName());
    }
}
