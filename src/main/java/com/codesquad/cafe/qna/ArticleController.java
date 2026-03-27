package com.codesquad.cafe.qna;

import com.codesquad.cafe.exception.ArticleInfoCannnotBeFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/qna")
public class ArticleController {

    private ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/form")
    public String qnaForm() {

        return "qna/form";
    }
    @PostMapping("/questions")
    public String question(@ModelAttribute Article article) {
        articleService.add(article);

        return "redirect:/qna/list";
    }

    @GetMapping("/list")
    public String qnaListForm(Model model) {
        List<Article> articleList = articleService.getArticles();
        model.addAttribute("articles", articleList);

        return "qna/list";
    }

    @GetMapping("/articles/{id}")
    public String qnaArticleForm(@PathVariable Long id, Model model) {
        try {
            Article article = articleService.findArticleById(id);
            model.addAttribute("article", article);
            return "qna/show";
        } catch (ArticleInfoCannnotBeFoundException e) {
            return "redirect:/qna/list";
        }
    }
}
