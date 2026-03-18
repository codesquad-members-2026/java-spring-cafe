package com.codesquad.article;

import com.codesquad.cafeRepo.ArticleRepo;
import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;

public class ArticleRepoTest {

    ArticleRepo testRepo = new ArticleRepo();

    @BeforeEach
    void setUp(){
        testRepo.clear();
    }

    @Test
    @DisplayName("Add an article and test finding it with id")
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
    @DisplayName("Add an article and test finding it with title")
    void testGetArticleByTitle(){
        Article newArticle1 = new Article("titleNew1","contentNew1");
        Article newArticle2 = new Article("titleNew2","contentNew2");
        testRepo.putNewArticle(newArticle1);
        testRepo.putNewArticle(newArticle2);
        assertThat(testRepo.getArticleWithTitle("titleNew1")).isEqualTo(newArticle1);
        assertThat(testRepo.getArticleWithTitle("titleNew2")).isEqualTo(newArticle2);
    }

}
