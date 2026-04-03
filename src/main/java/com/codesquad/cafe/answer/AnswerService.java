package com.codesquad.cafe.answer;

import com.codesquad.cafe.global.exception.NotOwnerException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
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
