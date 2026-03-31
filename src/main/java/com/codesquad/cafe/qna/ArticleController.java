package com.codesquad.cafe.qna;

import com.codesquad.cafe.exception.ArticleInfoCannnotBeFoundException;
import com.codesquad.cafe.qna.dto.ArticleListDTO;
import com.codesquad.cafe.qna.dto.ArticleWriteDTO;
import com.codesquad.cafe.user.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/qna")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/write")
    public String writeForm(
            @SessionAttribute(name = "sessionUser", required = false) User sessionUser,
            RedirectAttributes redirectAttributes) {

        if(sessionUser == null){
            redirectAttributes.addFlashAttribute("errorMessage",
                    "로그인 후에 이용할 수 있습니다!");
            return "redirect:/user/login";
        }

        return "qna/write";
    }
    @PostMapping("/write")
    public String write(
            @SessionAttribute(name = "sessionUser", required = false) User sessionUser,
            Model model, @Valid @ModelAttribute ArticleWriteDTO articleWriteDTO,
            BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if(sessionUser == null){
            redirectAttributes.addFlashAttribute("errorMessage", "세션 정보가 없습니다.");
            return "redirect:/user/login";
        }

        if(bindingResult.hasErrors()){
            model.addAttribute("article", articleWriteDTO);
            model.addAttribute("errorMessage", "제목과 본문을 모두 적어야 합니다.");
            return "qna/write";
        }

        articleService.add(articleWriteDTO.toEntity(sessionUser));
        return "redirect:/qna/list";
    }

    @GetMapping("/list")
    public String listForm(Model model) {
        List<ArticleListDTO> articleList = articleService.getArticleList();
        model.addAttribute("articles", articleList);

        return "qna/list";
    }

    @GetMapping("/articles/{id}")
    public String articleForm(
            @SessionAttribute(name = "sessionUser", required = false) User sessionUser,
            @PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {

        if(sessionUser == null){
            redirectAttributes.addFlashAttribute("errorMessage",
                    "로그인 후에 이용할 수 있습니다!");
            return "redirect:/qna/list";
        }

        try {
            Article article = articleService.findArticleById(id);
            model.addAttribute("article", article);
            return "qna/show";
        } catch (ArticleInfoCannnotBeFoundException e) {
            return "redirect:/qna/list";
        }
    }

    @GetMapping("/articles/{id}/edit")
    public String moveEditForm(
            @SessionAttribute(name = "sessionUser", required = false) User sessionUser,
            Model model, @PathVariable Long id, RedirectAttributes redirectAttributes){

        try {
            Article article = articleService.findArticleById(id);
            System.out.println(article.getWriter().getLoginId());
            if(Objects.equals(sessionUser.getId(), article.getWriter().getId())){ // TODO: 수정 필요
                model.addAttribute("article", article);
                return "qna/edit";
            }

            redirectAttributes.addFlashAttribute("errorMessage",
                    "해당 글을 쓴 작성자만 수정할 수 있습니다!");
            return "redirect:/qna/list";

        } catch (ArticleInfoCannnotBeFoundException e) {
            // TODO: addAttribute() vs addFlashAttribute() --> session 과의 연결점
            redirectAttributes.addFlashAttribute("errorMessage",
                    "존재하지 않는 게시글입니다!");
            return "redirect:/qna/list";
        }
    }

    // TODO: 글 수정
//    @PutMapping("/edit")
//    public String edit(){
//
//    }
}
