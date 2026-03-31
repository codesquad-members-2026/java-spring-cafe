package com.codesquad.cafe.question;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String getQuestions(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            model.addAttribute("loginUser", session.getAttribute("loginUser"));
        }

        model.addAttribute("questions", questionService.getAll());
        return "/question/questions";
    }

    @GetMapping("/create")
    public String createForm(@SessionAttribute(name = "loginUser", required = false) Long loginUserId) {
        if (loginUserId == null) {
            return "redirect:/users/login";
        }

        return "redirect:/question/form.html";
    }

    @PostMapping("/create")
    public String create(@SessionAttribute(name = "loginUser", required = false) Long loginUserId,
                         @ModelAttribute Question question) {
        if (loginUserId == null) {
            return "redirect:/users/login";
        }

        questionService.save(loginUserId, question);
        return "redirect:/questions";
    }

    @GetMapping("/{questionId}")
    public String getQuestionById(@SessionAttribute(name = "loginUser", required = false) Long loginUserId,
                                  @PathVariable Long questionId, Model model) {

        if (loginUserId == null) {
            return "redirect:/users/login";
        }
        QuestionDetail question = questionService.getDetail(questionId);

        model.addAttribute("title", question.getTitle());
        model.addAttribute("contents", question.getContents());
        model.addAttribute("author", question.getAuthor());
        return "/question/questions-detail";
    }

    @GetMapping("/{questionId}/edit")
    public String editForm(@SessionAttribute(name = "loginUser", required = false) Long loginUserId,
                           @PathVariable Long questionId, Model model, RedirectAttributes redirectAttributes) {

        if (loginUserId == null) {
            return "redirect:/users/login";
        }

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
        if (loginUserId == null) {
            return "redirect:/users/login";
        }

        try {
            questionService.update(questionId, loginUserId, question);
        } catch (Exception e) {
            return "redirect:/questions/" + questionId;
        }

        return "redirect:/questions/" + questionId;
    }
}