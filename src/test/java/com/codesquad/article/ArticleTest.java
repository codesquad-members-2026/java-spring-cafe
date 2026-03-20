package com.codesquad.article;

import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;

public class ArticleTest {

    @Test
    @DisplayName("Constructor and getter should work")
    void testCreation(){
        Article newArticle = new Article("title1","body1");
        assertThat(newArticle.getTitle()).isEqualTo("title1");
        assertThat(newArticle.getContent()).isEqualTo("body1");
    }
}
