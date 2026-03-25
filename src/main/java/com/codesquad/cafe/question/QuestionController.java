package com.codesquad.cafe.question;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("")
    public String create(@ModelAttribute Question question) {
        questionService.save(question);
        return "redirect:/questions";
    }

    @GetMapping("")
    public String getQuestions(Model model) {
        model.addAttribute("questions", questionService.getAll());
        return "questions";
    }

    @GetMapping("/{questionId}")
    public String getQuestionById(@PathVariable Long questionId, Model model) {
        Question question = questionService.get(questionId);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("contents", question.getContents());
        return "questions-detail";
    }
}
