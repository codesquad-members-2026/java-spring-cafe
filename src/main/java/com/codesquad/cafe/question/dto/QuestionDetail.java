package com.codesquad.cafe.question.dto;

import com.codesquad.cafe.answer.dto.AnswerDetail;
import com.codesquad.cafe.question.Question;
import java.util.List;

public class QuestionDetail {
    public Long id;
    public String title;
    public String content;
    public String author;
    public List<AnswerDetail> answers;
    public int answersCount;

    public QuestionDetail(Long id, String title, String content, String author, List<AnswerDetail> answers) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.answers = answers;
        this.answersCount = answers.size();
    }

    public Long getId() {
        return id;
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

    public int getAnswersCount() {
        return answersCount;
    }

    public List<AnswerDetail> getAnswers() {
        return answers;
    }

    public static QuestionDetail from(Question question, List<AnswerDetail> answers) {
        return new QuestionDetail(question.getId(), question.getTitle(),
                question.getContent(), question.getAuthor().getName(), answers);
    }
}
