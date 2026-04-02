package com.codesquad.cafe.question;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("")
    public String getQuestions(@SessionAttribute(name = "loginUser", required = false) Long loginUserId, Model model) {
        model.addAttribute("loginUser", loginUserId);

        model.addAttribute("questions", questionService.getAll());
        return "/question/questions";
    }

    @GetMapping("/create")
    public String createForm() {
        return "redirect:/question/form.html";
    }

    @PostMapping("/create")
    public String create(@SessionAttribute(name = "loginUser", required = false) Long loginUserId,
                         @ModelAttribute Question question) {
        questionService.save(loginUserId, question);
        return "redirect:/questions";
    }

    @GetMapping("/{questionId}")
    public String getQuestionById(@PathVariable Long questionId, Model model) {
        QuestionDetail question = questionService.getDetail(questionId);

        model.addAttribute("title", question.getTitle());
        model.addAttribute("contents", question.getContents());
        model.addAttribute("author", question.getAuthor());
        model.addAttribute("id", questionId);
        return "/question/questions-detail";
    }

    @GetMapping("/{questionId}/edit")
    public String editForm(@SessionAttribute(name = "loginUser", required = false) Long loginUserId,
                           @PathVariable Long questionId, Model model, RedirectAttributes redirectAttributes) {

        try {
            questionService.validateOwner(questionId, loginUserId);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/questions/" + questionId;
        }

        QuestionDetail question = questionService.getDetail(questionId);
        model.addAttribute("questionId", questionId);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("contents", question.getContents());
        return "/question/editForm";
    }

    @PutMapping("/{questionId}")
    public String editQuestion(@SessionAttribute(name = "loginUser", required = false) Long loginUserId,
                               @PathVariable Long questionId, @ModelAttribute Question question) {
        try {
            questionService.update(questionId, loginUserId, question);
        } catch (Exception e) {
            //수정 권한 없음
            return "redirect:/questions/" + questionId;
        }

        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{questionId}")
    public String delete(@SessionAttribute(name = "loginUser", required = false) Long loginUserId,
                         @PathVariable Long questionId, RedirectAttributes redirectAttributes) {
        try {
            questionService.delete(questionId, loginUserId);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/questions/" + questionId;
        }

        return "redirect:/questions";
    }
}