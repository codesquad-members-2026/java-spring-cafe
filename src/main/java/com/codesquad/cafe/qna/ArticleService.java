package com.codesquad.cafe.qna;

import com.codesquad.cafe.exception.ArticleInfoCannnotBeFoundException;
import com.codesquad.cafe.exception.UnauthorizedAccessException;
import com.codesquad.cafe.qna.dto.ArticleDetailsDTO;
import com.codesquad.cafe.qna.dto.ArticleListDTO;
import com.codesquad.cafe.qna.dto.ArticleWriteDTO;
import com.codesquad.cafe.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly=true)
public class ArticleService {
    private final JpaArticleRepository jpaArticleRepository;

    public ArticleService(JpaArticleRepository jpaArticleRepository) {
        this.jpaArticleRepository = jpaArticleRepository;
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
}
