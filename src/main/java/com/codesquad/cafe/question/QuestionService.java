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

    public List<QuestionSummary> getAll() {
        return repository.findAll().stream()
                .map(QuestionSummary::from)
                .toList();
    }

    public QuestionDetail getDetail(Long questionId) {
        Question question = repository.findById(questionId).get();
        return QuestionDetail.from(question);
    }
}
