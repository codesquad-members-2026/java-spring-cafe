package com.codesquad.cafe.question;

import com.codesquad.cafe.global.exception.NotOwnerException;
import com.codesquad.cafe.question.dto.QuestionDetail;
import com.codesquad.cafe.question.dto.QuestionSummary;
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

    public void validateOwner(Long questionId, Long loginUserId) {
        Question question = repository.findById(questionId).get();
        Long authorId = question.getAuthor().getId();

        if (!authorId.equals(loginUserId)) {
            throw new NotOwnerException();
        }
    }

    @Transactional
    public void update(Long questionId, Long loginUserId, Question updatedQuestion) {
        validateOwner(questionId, loginUserId);
        Question question = repository.findById(questionId).get();
        question.setTitle(updatedQuestion.getTitle());
        question.setContent(updatedQuestion.getContent());
    }

    @Transactional
    public void delete(Long questionId, Long loginUserId) {
        validateOwner(questionId, loginUserId);
        repository.deleteById(questionId);
    }
}
