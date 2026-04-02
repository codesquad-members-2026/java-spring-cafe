package com.codesquad.cafe.answer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    @GetMapping("/")
    public String getAnswers(@PathVariable String questionId, Model model) {

        return "/answer/answers";
    }
}
