package com.codesquad.cafe.qna;

import com.codesquad.cafe.exception.ArticleInfoCannnotBeFoundException;
import com.codesquad.cafe.qna.dto.ArticleDetailsDTO;
import com.codesquad.cafe.qna.dto.ArticleListDTO;
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

    public ArticleDetailsDTO findArticleById(Long id) {
        return jpaArticleRepository.findArticleWithWriterById(id)
                .map(article -> new ArticleDetailsDTO(article.getId(), article.getTitle(), article.getContents(),
                                article.getWriter().getLoginId(), article.getWriter().getId()))
                .orElseThrow(() -> new ArticleInfoCannnotBeFoundException("No article found with id" + id));
    }

    public List<ArticleListDTO> getArticleList(){
        List<Article> articleList = jpaArticleRepository.findAllWithWriter();

        return articleList.stream().map(article
                -> new ArticleListDTO(article.getId(), article.getTitle(), article.getWriter().getLoginId()))
                .toList();
    }
}
