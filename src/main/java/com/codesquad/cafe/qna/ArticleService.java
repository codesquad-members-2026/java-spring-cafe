package com.codesquad.cafe.qna;

import com.codesquad.cafe.exception.ArticleInfoCannnotBeFoundException;
import com.codesquad.cafe.qna.dto.ArticleListDTO;
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

    public Article findArticleById(Long id) {
        return jpaArticleRepository.findById(id)
                .orElseThrow(() -> new ArticleInfoCannnotBeFoundException("No article found with id" + id));
    }

    public List<ArticleListDTO> getArticleList(){
        List<Article> articleList = jpaArticleRepository.findAllWithWriter();

        return articleList.stream().map(article
                -> new ArticleListDTO(article.getId(), article.getTitle(), article.getWriter().getLoginId()))
                .toList();
    }

    public List<Article> getArticles() {
        return jpaArticleRepository.findAll();
    }
}
