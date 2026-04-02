package com.codesquad.cafe.answer.dto;

import com.codesquad.cafe.answer.Answer;

public class AnswerDetail {
    public Long id;
    public String content;
    public String author;

    private AnswerDetail(Long id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public static AnswerDetail from(Answer answer) {
        return new AnswerDetail(answer.getId(), answer.getContent(),
                answer.getAuthor().getName());
    }

}
