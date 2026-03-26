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
    public String getQuestionForm(HttpSession session, Model model){
        if(session.getAttribute("currentUser") == null){
            return "redirect:/user/login";
        }
        Article targetArticle = new Article();
        model.addAttribute("article", targetArticle);
        model.addAttribute("formActionUrl", "/qna/question");
        return "qna/submitQuestion";
    }

    @PostMapping("/question")
    public String postQuestionForm(@ModelAttribute Article article, HttpSession session){
        User currentUser = (User) session.getAttribute("currentUser");
        if(currentUser == null){
            return "redirect:/user/login";
        }
        article.setUser(currentUser);
        article.setAuthor();
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

    @GetMapping("/{id}/edit")
    public String getEditPageForArticle(@PathVariable int id, HttpSession session, Model model){
        if(session.getAttribute("currentUser") == null){
            return "redirect:/user/login";
        }

        Article targetArticle = service.findArticleById(id);
        model.addAttribute("article", targetArticle);
        model.addAttribute("formActionUrl", "/qna/"+id+"/edit");
        return "qna/submitQuestion";
    }

    @PostMapping("/{id}/edit")
    public String postEditedArticle(@PathVariable int id, HttpSession session, @ModelAttribute Article article){
        article.setUser((User)session.getAttribute("currentUser"));
        article.setAuthor();
        service.putNewArticle(article);
        return "redirect:/qna/"+id;
    }

    @DeleteMapping("/{id}/delete")
    public String deleteArticle(@PathVariable int id, HttpSession session, RedirectAttributes ra){
        Article targetArticle = service.findArticleById(id);
        if(!targetArticle.getUser().equals(session.getAttribute("currentUser"))){
            ra.addFlashAttribute("errorMessage", "YOU CANNOT DELETE OTHER'S ARTICLE");
            return "redirect:/qna/";
        }
        service.deleteArticle(targetArticle, (User)session.getAttribute("currentUser"));
        return "redirect:/qna/";
    }





}
