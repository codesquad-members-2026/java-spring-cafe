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
    public String getQuestionForm(@SessionAttribute(value="currentUser", required=false) User currentUser, Model model){
        if(currentUser == null){
            return "redirect:/user/login";
        }
        Article targetArticle = new Article();
        model.addAttribute("article", targetArticle);
        model.addAttribute("formActionUrl", "/qna/question");
        return "qna/submitQuestion";
    }

    @PostMapping("/question")
    public String postQuestionForm(@ModelAttribute Article article, @SessionAttribute(value="currentUser", required=false) User currentUser){
        if(currentUser == null){
            return "redirect:/user/login";
        }
        article.setUser(currentUser);
        article.setAuthor();
        service.putNewArticle(article);
        return "redirect:/qna/";
    }

    @GetMapping("/{articleId}")
    public String getArticleDetail(@PathVariable int articleId, Model model, @SessionAttribute(value="currentUser", required=false) User currentUser, RedirectAttributes redirectAttributes){

        if(currentUser == null){
            redirectAttributes.addFlashAttribute("errorMessage", "Please Log In to Read Questions");
            return "redirect:/user/login";
        }

        model.addAttribute("article", service.findArticleById(articleId));
        return "qna/questionDetail";
    }

    @GetMapping("/{id}/edit")
    public String getEditPageForArticle(@PathVariable int id, @SessionAttribute(value="currentUser", required=false) User currentUser, Model model, RedirectAttributes ra){
        if(currentUser == null){
            return "redirect:/user/login";
        }
        Article targetArticle = service.findArticleById(id);
        if(targetArticle.getUser().equals(currentUser)){
            model.addAttribute("article", targetArticle);
            model.addAttribute("formActionUrl", "/qna/"+id+"/edit");
            return "qna/submitQuestion";
        }
        else{
            ra.addFlashAttribute("errorMessage", "YOU CANNOT EDIT OTHER'S ARTICLE");
            return "redirect:/qna/";
        }

    }

    @PostMapping("/{id}/edit")
    public String postEditedArticle(@PathVariable int id, @SessionAttribute(value="currentUser", required=false) User currentUser, @ModelAttribute Article article){
        article.setUser(currentUser);
        article.setAuthor();
        service.putNewArticle(article);
        return "redirect:/qna/"+id;
    }

    @DeleteMapping("/{id}/delete")
    public String deleteArticle(@PathVariable int id, @SessionAttribute(value="currentUser", required=false) User currentUser, RedirectAttributes ra){
        Article targetArticle = service.findArticleById(id);
        if(!targetArticle.getUser().equals(currentUser)){
            ra.addFlashAttribute("errorMessage", "YOU CANNOT DELETE OTHER'S ARTICLE");
            return "redirect:/qna/";
        }
        service.deleteArticle(targetArticle, currentUser);
        return "redirect:/qna/";
    }





}
