package com.codesquad.cafe.question;

import com.codesquad.cafe.question.dto.QuestionDetail;
import com.codesquad.cafe.user.dto.LoginUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("")
    public String getQuestions(@SessionAttribute(name = "loginUser", required = false) LoginUser loginUser, Model model) {
        model.addAttribute("loginUser", loginUser);

        model.addAttribute("questions", questionService.getAll());
        return "/question/questions";
    }

    @GetMapping("/create")
    public String createForm() {
        return "redirect:/question/form.html";
    }

    @PostMapping("/create")
    public String create(@SessionAttribute(name = "loginUser", required = false) LoginUser loginUser,
                         @ModelAttribute Question question) {
        questionService.save(loginUser.getId(), question);
        return "redirect:/questions";
    }

    @GetMapping("/{questionId}")
    public String getQuestionById(@SessionAttribute(name = "loginUser", required = false) LoginUser loginUser,
                                  @PathVariable Long questionId, Model model) {
        model.addAttribute("loginUser", loginUser);
        QuestionDetail question = questionService.getDetail(questionId);
        model.addAttribute("question", question);
        return "/question/questions-detail";
    }

    @GetMapping("/{questionId}/edit")
    public String editForm(@SessionAttribute(name = "loginUser", required = false) LoginUser loginUser,
                           @PathVariable Long questionId, Model model) {

        questionService.validateOwner(questionId, loginUser.getId());

        QuestionDetail question = questionService.getDetail(questionId);
        model.addAttribute("questionId", questionId);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("content", question.getContent());
        return "/question/editForm";
    }

    @PutMapping("/{questionId}")
    public String editQuestion(@SessionAttribute(name = "loginUser", required = false) LoginUser loginUser,
                               @PathVariable Long questionId, @ModelAttribute Question question) {
        questionService.update(questionId, loginUser.getId(), question);

        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{questionId}")
    public String delete(@SessionAttribute(name = "loginUser", required = false) LoginUser loginUser,
                         @PathVariable Long questionId) {
        questionService.delete(questionId, loginUser.getId());

        return "redirect:/questions";
    }
}