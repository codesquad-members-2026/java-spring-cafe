package com.codesquad.service;

import com.codesquad.article.Article;
import com.codesquad.cafeRepo.ArticleRepo;
import com.codesquad.cafeRepo.JpaArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ArticleService {

    private JpaArticleRepo repo;

    @Autowired
    public ArticleService(JpaArticleRepo repo){
        this.repo = repo;
    }

    public List<Article> getAllArticles(){
        return repo.findAll();
    }

    public void putNewArticle(Article newArticle){
        repo.save(newArticle);
    }

    public Article findArticleById(int id){
        return this.repo.findArticleById(id);
    }

}
