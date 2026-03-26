package com.codesquad.article;

import com.codesquad.user.User;
import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;

public class ArticleTest {

    @Test
    @DisplayName("Constructor and getter should work")
    void testCreation(){
        User testUser = new User();
        testUser.setName("Tester");
        testUser.setId("testID");
        testUser.setPassword("testpassword");
        testUser.setEmail("test@test.com");
        Article newArticle = new Article();
        newArticle.setTitle("title1");
        newArticle.setContent("body1");
        newArticle.setUser(testUser);
        newArticle.setAuthor();
        assertThat(newArticle.getTitle()).isEqualTo("title1");
        assertThat(newArticle.getContent()).isEqualTo("body1");
        assertThat(newArticle.getUser().equals(testUser));
        assertThat(newArticle.getAuthor().equals(testUser.getId()));
    }
}
