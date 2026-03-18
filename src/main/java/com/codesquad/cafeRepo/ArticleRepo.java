package com.codesquad.cafeRepo;

import com.codesquad.article.Article;

import java.util.HashMap;
import java.util.Map;

public class ArticleRepo {

    private final Map<Integer, Article> idToArticleMap = new HashMap<>();
    private final Map<String, Article> titleToArticleMap = new HashMap<>();
    private int nextId = 1;

    public void putNewArticle(Article newArticle){
        newArticle.setId(nextId);
        nextId++;
        this.idToArticleMap.put(newArticle.getId(), newArticle);
        this.titleToArticleMap.put(newArticle.getTitle(), newArticle);
    }

    public Article getArticleWithId(int id){
        if(this.idToArticleMap.containsKey(id)){
            return idToArticleMap.get(id);
        }
        return null;
    }

    public Article getArticleWithTitle(String title){
        if(this.titleToArticleMap.containsKey(title)){
            return this.titleToArticleMap.get(title);
        }
        return null;
    }

    public void clear(){
        this.titleToArticleMap.clear();
        this.idToArticleMap.clear();
    }

}
