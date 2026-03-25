package com.codesquad.cafe.controller;

import com.codesquad.cafe.domain.Article;
import com.codesquad.cafe.repository.ArticleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class ArticleController {


    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @PostMapping("/questions")
    public String createArticle(Article article) {
        articleRepository.save(article);
        return "redirect:/";
    }

    @GetMapping("/questions/form")
    public String form() {
        return "qna/form";
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("articles", articleRepository.findAll());
        return "qna/index";
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        Optional<Article> article = articleRepository.findById(id);

        if (article.isPresent()) {
            model.addAttribute("article", article.get());
            return "qna/show";
        }

        return "redirect:/";
    }
}