package com.codesquad.cafe.answer;

import com.codesquad.cafe.global.exception.NotOwnerException;
import com.codesquad.cafe.question.QuestionRepository;
import com.codesquad.cafe.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    public AnswerService(AnswerRepository answerRepository,QuestionRepository questionRepository,
                         UserRepository userRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void save(Answer answer, Long authorId, Long questionId) {
        answer.setAuthor(userRepository.findById(authorId).get());
        answer.setQuestion(questionRepository.findById(questionId).get());
        answerRepository.save(answer);
    }

    @Transactional
    public void delete(Long loginUserId, Long answerId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new EntityNotFoundException("댓글이 존재하지 않음"));

        if (!answer.getAuthor().getId().equals(loginUserId)) {
            throw new NotOwnerException();
        }

        answerRepository.delete(answer);
    }
}
