package com.codesquad.service;

import com.codesquad.article.Article;
import com.codesquad.cafeRepo.JpaArticleRepo;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

    @Mock
    private JpaArticleRepo repo;

    @InjectMocks
    private ArticleService service;

    @Test
    @DisplayName("Should Return Article When Queried With ID")
    void findArticleByIdTest(){
        Article testArticle = new Article();

        testArticle.setId(1);
        testArticle.setTitle("title1");
        testArticle.setContent("content1");

        when(repo.findArticleById(1)).thenReturn(testArticle);

        Article returnedArticle = repo.findArticleById(1);

        assertThat(returnedArticle.getTitle()).isEqualTo(testArticle.getTitle());
        assertThat(returnedArticle.getContent()).isEqualTo(testArticle.getContent());
        verify(repo, times(1)).findArticleById(1);

    }

}
