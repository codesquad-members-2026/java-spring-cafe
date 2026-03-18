package com.codesquad.service;

import com.codesquad.article.Article;
import com.codesquad.cafeRepo.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;

public class ArticleService {
    @Autowired
    ArticleRepo repo;

    public Article[] getAllArticles(){
        return repo.getAllArticles();
    }

    public void putNewArticle(Article newArticle){
        repo.putNewArticle(newArticle);
    }

}
