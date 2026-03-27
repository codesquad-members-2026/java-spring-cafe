package com.codesquad.cafe.question;

import com.codesquad.cafe.user.User;
import com.codesquad.cafe.user.UserService;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    private final QuestionRepository repository;
    private final UserService userService;

    public QuestionService(QuestionRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @Transactional
    public void save(Long loginUserId, Question question) {
        User user = userService.get(loginUserId);
        question.setAuthor(user);

        repository.save(question);
    }

    public List<Question> getAll() {
        return repository.findAll();
    }

    public Question get(Long questionId) {
        return repository.findById(questionId).get();
    }

}
