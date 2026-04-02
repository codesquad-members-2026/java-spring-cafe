package com.codesquad.cafe.question.dto;

import com.codesquad.cafe.question.Question;
import java.util.List;

public class QuestionDetail {
    public String title;
    public String content;
    public String author;
    //public List<AnswerDetail> answers;

    private QuestionDetail(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public static QuestionDetail from(Question question) {
        return new QuestionDetail(question.getTitle(),
                question.getContent(), question.getAuthor().getName());
    }
}
