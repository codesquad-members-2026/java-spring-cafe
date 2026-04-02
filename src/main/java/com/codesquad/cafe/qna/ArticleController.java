package com.codesquad.cafe.qna;

import com.codesquad.cafe.exception.*;
import com.codesquad.cafe.qna.dto.*;
import com.codesquad.cafe.user.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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
        List<ArticleDetailsDTO> articleList = articleService.getArticleList();
        model.addAttribute("articles", articleList);

        return "qna/list";
    }

    @GetMapping("/articles/{id}")
    public String articleForm(
            @SessionAttribute(name = "sessionUser", required = false) User sessionUser,
            @PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {

        if(sessionUser == null){
            redirectAttributes.addFlashAttribute("errorMessage", "로그인 후에 이용할 수 있습니다!");
            return "redirect:/user/login";
        }

        try {
            ArticleDetailsDTO articleDetails = articleService.findArticleForDetails(id);
            List<CommentDetailsDTO> commentList = articleService.findCommentList(id);
            model.addAttribute("article", articleDetails);
            model.addAttribute("comments", commentList);
            return "qna/show";
        } catch (ArticleInfoCannnotBeFoundException e) {
            return "redirect:/qna/list";
        }
    }

    @GetMapping("/articles/{id}/edit")
    public String editForm(
            @SessionAttribute(name = "sessionUser", required = false) User sessionUser,
            Model model, @PathVariable Long id, RedirectAttributes redirectAttributes){

        if(sessionUser == null){
            redirectAttributes.addFlashAttribute("errorMessage", "로그인 후에 이용할 수 있습니다!");
            return "redirect:/user/login";
        }

        try {
            ArticleDetailsDTO article = articleService.findArticleForEdit(id, sessionUser);
            model.addAttribute("article", article);
            return "qna/edit";

        } catch (UnauthorizedAccessException ue) {
            redirectAttributes.addFlashAttribute("errorMessage", ue.getMessage());
            return "redirect:/qna/articles/" + id;
        } catch (ArticleInfoCannnotBeFoundException ae){
            redirectAttributes.addFlashAttribute("errorMessage", ae.getMessage());
            return "redirect:/qna/list";
        }
    }

    @PutMapping("/articles/{id}/edit")
    public String edit(@SessionAttribute(name = "sessionUser", required = false) User sessionUser,
                       RedirectAttributes redirectAttributes, Model model, @PathVariable Long id,
                       @Valid @ModelAttribute ArticleWriteDTO articleWriteDTO, BindingResult bindingResult) {

        if(sessionUser == null){
            redirectAttributes.addFlashAttribute("errorMessage", "세션 정보가 없습니다.");
            return "redirect:/user/login";
        }

        if(bindingResult.hasErrors()){
            model.addAttribute("article", articleWriteDTO);
            model.addAttribute("id", id);
            model.addAttribute("errorMessage", "제목과 본문을 모두 적어야 합니다.");
            return "qna/edit";
        }

        try {
            articleService.editArticle(id, articleWriteDTO, sessionUser);
            redirectAttributes.addFlashAttribute("successMessage",
                    "성공적으로 " + id + "번 게시물을 수정했습니다!");
            return "redirect:/qna/list";
        } catch (ArticleInfoCannnotBeFoundException | UnauthorizedAccessException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/qna/list";
        }
    }

    @DeleteMapping("/articles/{id}/delete")
    public String delete(@SessionAttribute(name = "sessionUser", required = false) User sessionUser,
                         RedirectAttributes redirectAttributes, @PathVariable Long id) {

        if(sessionUser == null){
            redirectAttributes.addFlashAttribute("errorMessage", "세션 정보가 없습니다.");
            return "redirect:/user/login";
        }

        try {
            articleService.deleteArticle(id, sessionUser);
            redirectAttributes.addFlashAttribute("successMessage",
                    "성공적으로 " + id + "번 게시물을 삭제했습니다!");
            return "redirect:/qna/articles/" + id;
        } catch (ArticleInfoCannnotBeFoundException ae) {
            redirectAttributes.addFlashAttribute("errorMessage", ae.getMessage());
            return "redirect:/qna/list";
        } catch (UnauthorizedAccessException | UnabletoDeleteArticleInfo e){
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/qna/articles/" + id;
        }
    }

    @PostMapping("/articles/{id}/comments")
    public String comment(@SessionAttribute(name = "sessionUser", required = false) User sessionUser,
                          RedirectAttributes redirectAttributes, @PathVariable Long id, Model model,
                          @Valid @ModelAttribute CommentWriteDTO commentWriteDTO, BindingResult bindingResult) {

        if(sessionUser == null){
            redirectAttributes.addFlashAttribute("세션이 만료됐습니다.");
            return "redirect:/user/login/";
        }

        if(bindingResult.hasErrors()){
            model.addAttribute("errorMessage", "잘못된 형식입니다.");
            return "redirect:/qna/articles/" + id;
        }

        try {
            articleService.addComment(commentWriteDTO, sessionUser, id);
            return "redirect:/qna/articles/" + id;
        } catch (ArticleInfoCannnotBeFoundException ae){
            redirectAttributes.addFlashAttribute("successMessage",
                    "게시글이 존재하지 않습니다.");
            return "redirect:/qna/list";
        }
    }

    @DeleteMapping("/articles/{articleId}/comments/{commentId}/delete")
    public String deleteComment(@SessionAttribute(name = "sessionUser", required = false) User sessionUser,
                                RedirectAttributes redirectAttributes, @PathVariable Long articleId,
                                @PathVariable Long commentId) {

        if(sessionUser == null){
            redirectAttributes.addFlashAttribute("errorMessage", "세션이 만료됐습니다.");
            return "redirect:/user/login/";
        }

        try {
            articleService.deleteComment(sessionUser, articleId, commentId);
            return "redirect:/qna/articles/" + articleId;

        } catch (CommentInfoCannotBeFoundException | UnableToDeleteCommentInfo ce){
            redirectAttributes.addFlashAttribute("errorMessage", ce.getMessage());
            return "redirect:/qna/articles/" + articleId;
        } catch (CommentDoNotExistInTheArticleException ce){
            redirectAttributes.addFlashAttribute("errorMessage", ce.getMessage());
            return "redirect:/qna/list";
        }
    }
}
