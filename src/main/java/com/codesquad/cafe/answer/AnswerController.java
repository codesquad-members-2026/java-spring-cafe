package com.codesquad.cafe.answer;

import com.codesquad.cafe.user.dto.LoginUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @DeleteMapping("/{answerId}")
    public String getAnswers(@SessionAttribute(name = "loginUser", required = false) LoginUser loginUser,
                             @PathVariable Long questionId, @PathVariable Long answerId) {
        answerService.delete(loginUser.getId(), answerId);
        return "redirect:/questions/" + questionId;
    }
}
