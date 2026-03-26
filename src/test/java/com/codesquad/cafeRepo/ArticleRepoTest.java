package com.codesquad.cafeRepo;

import com.codesquad.article.Article;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class ArticleRepoTest {

    @Autowired
    private JpaArticleRepo testRepo;

    @Test
    @DisplayName("Should Save and Retrieve Article By ID")
    void testSaveAndGetArticleByID(){
        Article newArticle = new Article();
        newArticle.setTitle("testTitle");
        newArticle.setContent("testContent");

        Article saveReturnedArticle = testRepo.save(newArticle);
        Article foundArticle = testRepo.findArticleById(saveReturnedArticle.getId());

        assertThat(foundArticle).isNotNull();
        assertThat(foundArticle.getTitle()).isEqualTo("testTitle");
        assertThat(foundArticle.getContent()).isEqualTo("testContent");

    }

}