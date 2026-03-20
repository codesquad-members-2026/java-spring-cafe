package com.codesquad.cafe.qna;

import com.codesquad.cafe.exception.NoArticleInListException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ArticleController {

    private ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/qna/form")
    public String qnaForm() {

        return "qna/form";
    }

    @PostMapping("/questions")
    public String question(@ModelAttribute Article article) {
        articleService.add(article);

        return "redirect:/qna/list";
    }

    @GetMapping("qna/list")
    public String qnaListForm(Model model) {
        List<Article> articleList = articleService.getArticles();
        model.addAttribute("articles", articleList);

        return "qna/list";
    }

    @GetMapping("/articles/{id}")
    public String qnaArticleForm(@PathVariable Integer id, Model model) {
        try {
            Article article = articleService.findById(id);
            model.addAttribute("article", article);
            return "qna/show";
        } catch (NoArticleInListException e) {
            return "qna/list";
        }
    }
}
