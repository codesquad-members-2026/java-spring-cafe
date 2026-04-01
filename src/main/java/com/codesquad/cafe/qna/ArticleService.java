package com.codesquad.cafe.qna;

import com.codesquad.cafe.exception.ArticleInfoCannnotBeFoundException;
import com.codesquad.cafe.exception.UnauthorizedAccessException;
import com.codesquad.cafe.qna.dto.*;
import com.codesquad.cafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly=true)
public class ArticleService {
    private final JpaArticleRepository jpaArticleRepository;
    private final JpaCommentRepository jpaCommentRepository;

    public ArticleService(JpaArticleRepository jpaArticleRepository, JpaCommentRepository jpaCommentRepository) {
        this.jpaArticleRepository = jpaArticleRepository;
        this.jpaCommentRepository = jpaCommentRepository;
    }

    public Long size(){
        return jpaArticleRepository.count();
    }

    @Transactional
    public void add(Article article) {
        jpaArticleRepository.save(article);
    }

    public List<ArticleListDTO> getArticleList(){
        List<Article> articleList = jpaArticleRepository.findAllWithWriter();

        return articleList.stream().map(article
                        -> new ArticleListDTO(article.getId(), article.getTitle(), article.getWriter().getLoginId()))
                .toList();
    }

    public ArticleDetailsDTO findArticleForDetails(Long id) {
        return jpaArticleRepository.findArticleWithWriterById(id)
                .map(article -> new ArticleDetailsDTO(article.getId(), article.getTitle(), article.getContents(),
                        article.getWriter().getLoginId(), article.getWriter().getId()))
                .orElseThrow(() -> new ArticleInfoCannnotBeFoundException("존재하지 않는 게시글입니다!"));
    }

    public ArticleDetailsDTO findArticleForEdit(Long id, User sessionUser) {
        Article article = getArticleForUpdate(id, sessionUser);

        return new ArticleDetailsDTO(article.getId(), article.getTitle(), article.getContents(),
                article.getWriter().getLoginId(), article.getWriter().getId());
    }

    @Transactional
    public void editArticle(Long id, ArticleWriteDTO articleWriteDTO, User sessionUser) {
        getArticleForUpdate(id, sessionUser)
                .updateTitleAndContents(articleWriteDTO.getTitle(), articleWriteDTO.getContents());
    }

    @Transactional
    public void deleteArticle(Long id, User sessionUser) {
        Article article = getArticleForUpdate(id, sessionUser);

        jpaCommentRepository.deleteAllByArticle_Id(id);

        jpaArticleRepository.delete(article);
    }

    private Article getArticleForUpdate(Long id, User sessionUser){
        Article article = jpaArticleRepository.findArticleWithWriterById(id)
                .orElseThrow(() -> new ArticleInfoCannnotBeFoundException("존재하지 않는 게시글입니다!"));

        if(!article.isWrittenBy(sessionUser)){
            throw new UnauthorizedAccessException("권한이 없습니다.");
        }

        return article;
    }

    @Transactional
    public void addComment(CommentWriteDTO commentWriteDTO, User sessionUser, Long articleId) {
        Article article = jpaArticleRepository.findArticleWithWriterById(articleId)
                .orElseThrow(() -> new ArticleInfoCannnotBeFoundException("존재하지 않는 게시글입니다!"));

        jpaCommentRepository.save(commentWriteDTO.toEntity(LocalDateTime.now(), sessionUser, article));
    }

    public List<CommentDetailsDTO> findCommentList(Long articleId) {
        return jpaCommentRepository.findByArticle_Id(articleId).stream().map(CommentDetailsDTO::new).toList();
    }
}
