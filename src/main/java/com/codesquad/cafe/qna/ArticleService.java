package com.codesquad.cafe.qna;

import com.codesquad.cafe.exception.NoArticleInListException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {
    private List<Article> articles;

    ArticleService() {
        this.articles = new ArrayList<>();
    }

    public int size(){
        return this.articles.size();
    }

    public void add(Article article) {
        article.setId(size() + 1);
        articles.add(article);
    }

    public Article findById(Integer id) {
        for(Article article : articles){
            if(article.getId().equals(id)){
                return article;
            }
        }

        throw new NoArticleInListException("존재하지 않는 게시글");
    }

    public List<Article> getArticles() {
        return articles;
    }
}
