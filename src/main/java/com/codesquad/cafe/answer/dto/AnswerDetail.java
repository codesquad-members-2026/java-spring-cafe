package com.codesquad.cafe.answer.dto;

import com.codesquad.cafe.answer.Answer;

public class AnswerDetail {
    public Long id;
    public String content;
    public String author;
    public boolean isOwner;

    private AnswerDetail(Long id, String content, String author,boolean isOwner) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.isOwner = isOwner;
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

    public static AnswerDetail from(Answer answer,Long loginUserId) {
        return new AnswerDetail(answer.getId(), answer.getContent(),
                answer.getAuthor().getName(), answer.getAuthor().getId().equals(loginUserId));
    }
}
