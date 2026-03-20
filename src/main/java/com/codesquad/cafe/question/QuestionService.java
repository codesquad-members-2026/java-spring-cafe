package com.codesquad.cafe.question;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    private final QuestionRepository repository;

    public QuestionService(QuestionRepository repository) {
        this.repository = repository;
    }

    public void save(Question question) {
        repository.save(question);
    }

    public List<Question> getAll() {
        return repository.findAll();
    }

    public Question get(Long questionId) {
        return repository.findById(questionId).get();
    }

}
