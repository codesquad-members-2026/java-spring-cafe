package com.codesquad.cafe.question;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    private final List<Question> questions = new ArrayList<>();

    public void save(Question question) {
        question.setId(questions.size() + 1);
        questions.add(question);
    }

    public List<Question> getAll() {
        return questions;
    }

    public Question get(int questionId) {
        return questions.get(questionId - 1);
    }
}
