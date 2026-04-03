package com.codesquad.cafe.answer;

import com.codesquad.cafe.user.dto.LoginUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping("")
    public String createAnswer(@SessionAttribute(name = "loginUser", required = false) LoginUser loginUser,
                               @PathVariable Long questionId, @ModelAttribute Answer answer) {
        answerService.save(answer, loginUser.getId(), questionId);
        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{answerId}")
    public String deleteAnswer(@SessionAttribute(name = "loginUser", required = false) LoginUser loginUser,
                             @PathVariable Long questionId, @PathVariable Long answerId) {
        answerService.delete(loginUser.getId(), answerId);
        return "redirect:/questions/" + questionId;
    }
}
