package com.codesquad.cafeController;

import com.codesquad.article.Article;
import com.codesquad.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String getQuestionForm(){
        return "qna/submitQuestion";
    }

    @PostMapping("/question")
    public String postQuestionForm(@ModelAttribute Article article){
        service.putNewArticle(article);
        return "redirect:";
    }

    @GetMapping("/{articleId}")
    public String getArticleDetail(@PathVariable int articleId, Model model){
        model.addAttribute("article", service.findArticleById(articleId));
        return "qna/questionDetail";
    }

}
