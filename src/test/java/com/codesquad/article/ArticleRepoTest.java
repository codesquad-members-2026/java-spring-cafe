package com.codesquad.article;

import com.codesquad.cafeRepo.ArticleRepo;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

public class ArticleRepoTest {

    ArticleRepo testRepo = new ArticleRepo();

    @BeforeEach
    void setUp(){
        testRepo.clear();
    }

    @Test
    @DisplayName("Finding article with id int value")
    void testGetArticleById(){
        Article newArticle1 = new Article("titleNew1","contentNew1");
        Article newArticle2 = new Article("titleNew2","contentNew2");
        testRepo.putNewArticle(newArticle1);
        testRepo.putNewArticle(newArticle2);
        assertThat(testRepo.getArticleWithId(1)).isEqualTo(newArticle1);
        assertThat(testRepo.getArticleWithId(1).getId()).isEqualTo(1);
        assertThat(testRepo.getArticleWithId(2)).isEqualTo(newArticle2);
        assertThat(testRepo.getArticleWithId(2).getId()).isEqualTo(2);
    }

    @Test
    @DisplayName("Finding article with title string")
    void testGetArticleByTitle(){
        Article newArticle1 = new Article("titleNew1","contentNew1");
        Article newArticle2 = new Article("titleNew2","contentNew2");
        testRepo.putNewArticle(newArticle1);
        testRepo.putNewArticle(newArticle2);
        assertThat(testRepo.getArticleWithTitle("titleNew1")).isEqualTo(newArticle1);
        assertThat(testRepo.getArticleWithTitle("titleNew2")).isEqualTo(newArticle2);
    }

    @Test
    @DisplayName("Retrieving all Articles as array from repo")
    void testGetAllArticle(){
        Article newArticle1 = new Article("titleNew1","contentNew1");
        Article newArticle2 = new Article("titleNew2","contentNew2");
        Article newArticle3 = new Article("titleNew3","contentNew3");
        testRepo.putNewArticle(newArticle1);
        testRepo.putNewArticle(newArticle2);
        testRepo.putNewArticle(newArticle3);
        Article[] currentArticlesInRepo = testRepo.getAllArticles();
        Set<Article> resultSet = new HashSet<>(Arrays.asList(currentArticlesInRepo));
        assertThat(currentArticlesInRepo.length).isEqualTo(3);
        assertThat(resultSet.contains(newArticle1)).isEqualTo(true);
        assertThat(resultSet.contains(newArticle2)).isEqualTo(true);
        assertThat(resultSet.contains(newArticle3)).isEqualTo(true);
    }



}
