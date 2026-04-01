package com.codesquad.cafe.controller;


import com.codesquad.cafe.domain.Article;
import com.codesquad.cafe.domain.Reply;
import com.codesquad.cafe.domain.User;
import com.codesquad.cafe.repository.ArticleRepository;
import com.codesquad.cafe.repository.ReplyRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class ReplyController {

    private final ReplyRepository replyRepository;
    private final ArticleRepository articleRepository;

    public ReplyController(ReplyRepository replyRepository, ArticleRepository articleRepository, ArticleRepository articleRepository1) {
        this.replyRepository = replyRepository;
        this.articleRepository = articleRepository1;
    }


    @PostMapping("/articles/{articleId}/replies")
    public String reply(@PathVariable("articleId") Long articleId, String contents, Model model, HttpSession session) {
        Optional<Article> article = articleRepository.findById(articleId);
        User replyUser = (User) session.getAttribute("sessionedUser");

        if ( replyUser == null) {
            return "redirect:/users/loginForm";
        }

        if (article.isPresent()) {
        Reply reply = new Reply(article.get(), replyUser, contents);
           replyRepository.save(reply);
            return "redirect:/articles/" + articleId;
        } else {
            return "redirect:/";
        }
    }

    @DeleteMapping("/articles/{articleId}/replies/{id}")
    public String delete(@PathVariable("articleId") Long articleId,
                         @PathVariable("id") Long id,
                         HttpSession session, RedirectAttributes rttr) {

        User sessionedUser = (User) session.getAttribute("sessionedUser");
        if (sessionedUser == null) {
            return "redirect:/users/loginForm";
        }

        Reply reply = replyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));

        if (!reply.getWriter().getUserId().equals(sessionedUser.getUserId())) {
            rttr.addFlashAttribute("errorMessage", "자신의 댓글만 삭제할 수 있습니다.");
            return "redirect:/articles/" + articleId;
        }
        replyRepository.delete(reply);
        return "redirect:/articles/" + articleId;
    }
}
