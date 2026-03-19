package com.codesquad.cafe.controller;

import com.codesquad.cafe.domain.Article;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ArticleController {

    private final List<Article> articles = new ArrayList<>();


    @PostMapping("/questions")
    public String createArticle(Article article) {
        articles.add(article);
        return "redirect:/";
    }

    @GetMapping("/questions/form")
    public String form() {
        return "qna/form";
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("articles", articles);
        return "qna/index";
    }
}
