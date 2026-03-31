package com.codesquad.cafe.controller;

import com.codesquad.cafe.domain.Article;
import com.codesquad.cafe.domain.User;
import com.codesquad.cafe.repository.ArticleRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class ArticleController {


    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @PostMapping("/questions")
    public String createArticle(Article article, HttpSession session) {
        User sessionedUser = (User) session.getAttribute("sessionedUser");
        if (sessionedUser == null) {
            return "redirect:/users/loginForm";
        }
        article.setWriter(sessionedUser);
        articleRepository.save(article);
        return "redirect:/";
    }

    @GetMapping("/questions/form")
    public String form(HttpSession session) {
        User sessionedUser = (User) session.getAttribute("sessionedUser");
        if (sessionedUser == null) {
            return "redirect:/users/loginForm";
        }
        return "qna/form";
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("articles", articleRepository.findAll());
        return "qna/index";
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable("id") Long id, Model model, HttpSession session) {
        Optional<Article> article = articleRepository.findById(id);
        User sessionedUser = (User) session.getAttribute("sessionedUser");

        if (sessionedUser == null) {
            return "redirect:/users/loginForm";
        }

        if (article.isPresent()) {
            model.addAttribute("article", article.get());
            return "qna/show";
        }

        return "redirect:/";
    }

    @GetMapping("/articles/{id}/form")
    public String updateForm(@PathVariable("id") Long id, Model model, HttpSession session, RedirectAttributes rttr) {
        Optional<Article> article = articleRepository.findById(id);
        User sessionedUser = (User) session.getAttribute("sessionedUser");

        if (sessionedUser == null) {
            return "redirect:/users/loginForm";
        }
        if (article.isPresent()) {
            Article targetArticle = article.get();
            if (targetArticle.getWriter().getUserId().equals(sessionedUser.getUserId())) {
                model.addAttribute("article", targetArticle);
                return "qna/updateForm";
            } else {
                rttr.addFlashAttribute("errorMessage", "자신의 게시글만 수정할 수 있습니다.");
                return "redirect:/";
            }
        }
        return "redirect:/";
    }

    @PutMapping("/articles/{id}")
    public String update(@PathVariable("id") Long id, Article updateArticle, HttpSession session, RedirectAttributes rttr) {
        User sessionedUser = (User) session.getAttribute("sessionedUser");
        if (sessionedUser == null) {
            return "redirect:/users/loginForm";
        }
        Optional<Article> article = articleRepository.findById(id);
        if (!article.isPresent()) {
            rttr.addFlashAttribute("errorMessage", "존재하지 않는 게시글입니다.");
            return "redirect:/";
        }

        Article targetArticle = article.get();
        if (!targetArticle.getWriter().getUserId().equals(sessionedUser.getUserId())) {
            rttr.addFlashAttribute("errorMessage", "자신의 게시글만 수정할 수 있습니다.");
            return "redirect:/articles/" + id;
        }

        targetArticle.setTitle(updateArticle.getTitle());
        targetArticle.setContents(updateArticle.getContents());

        articleRepository.save(targetArticle);

        rttr.addFlashAttribute("successMessage", "게시글이 성공적으로 수정되었습니다.");
        return "redirect:/articles/" + id;
    }

    @DeleteMapping("/articles/{id}")
    public String delete(@PathVariable("id") Long id, HttpSession session, RedirectAttributes rttr) {
        User sessionedUser = (User) session.getAttribute("sessionedUser");
        if (sessionedUser == null) {
            return "redirect:/users/loginForm";
        }

        Optional<Article> article = articleRepository.findById(id);
        if (!article.isPresent()) {
            rttr.addFlashAttribute("errorMessage", "존재하지 않는 게시글입니다.");
            return "redirect:/";
        }

        Article targetArticle = article.get();

        if (!targetArticle.getWriter().getUserId().equals(sessionedUser.getUserId())) {
            rttr.addFlashAttribute("errorMessage", "자신의 게시글만 삭제할 수 있습니다.");
            return "redirect:/articles/" + id;
        }

        articleRepository.delete(targetArticle);

        rttr.addFlashAttribute("successMessage", "게시글이 성공적으로 삭제되었습니다.");
        return "redirect:/";
    }
}