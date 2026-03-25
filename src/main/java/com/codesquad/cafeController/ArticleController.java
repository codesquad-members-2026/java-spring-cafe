package com.codesquad.cafeController;

import com.codesquad.article.Article;
import com.codesquad.service.ArticleService;
import com.codesquad.user.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/qna")
public class ArticleController {

    @Autowired
    ArticleService service;

    @GetMapping("/")
    public String getQna(Model model){
        model.addAttribute("articles", service.getAllArticles());
        return "qna/qnaList";
    }

    @GetMapping("/question")
    public String getQuestionForm(HttpSession session){
        if(session.getAttribute("currentUser") == null){
            return "redirect:/user/login";
        }
        return "qna/submitQuestion";
    }

    @PostMapping("/question")
    public String postQuestionForm(@ModelAttribute Article article, HttpSession session){
        article.setAuthor(((User)session.getAttribute("currentUser")).getId());
        service.putNewArticle(article);
        return "redirect:/qna/";
    }

    @GetMapping("/{articleId}")
    public String getArticleDetail(@PathVariable int articleId, Model model, HttpSession session, RedirectAttributes redirectAttributes){

        if(session.getAttribute("currentUser") == null){
            redirectAttributes.addFlashAttribute("errorMessage", "Please Log In to Read Questions");
            return "redirect:/user/login";
        }

        model.addAttribute("article", service.findArticleById(articleId));
        return "qna/questionDetail";
    }


}
