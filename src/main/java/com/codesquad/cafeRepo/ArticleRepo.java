package com.codesquad.cafeRepo;

import com.codesquad.article.Article;

import java.util.HashMap;
import java.util.Map;

public class ArticleRepo {

    private final Map<Integer, Article> idToArticleMap = new HashMap<>();
    private final Map<String, Article> titleToArticleMap = new HashMap<>();


    public void putNewArticle(Article newArticle){
        newArticle.setId(idToArticleMap.size()+1);
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

    public Article[] getAllArticles(){
        return this.idToArticleMap.values().toArray(new Article[0]);
    }

    public void clear(){
        this.titleToArticleMap.clear();
        this.idToArticleMap.clear();
    }

}
