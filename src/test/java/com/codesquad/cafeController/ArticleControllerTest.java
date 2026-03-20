//package com.codesquad.cafeController;
//
//import com.codesquad.article.Article;
//import com.codesquad.service.ArticleService;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Collections;
//
//
//import static org.assertj.core.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//
//
//@WebMvcTest(ArticleController.class)
//public class ArticleControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockitoBean
//    private ArticleService articleService;
//
//    @Test
//    @DisplayName("GET \"/\" should return the list of existing QnA articles")
//    void testGetDefaultQnaList() throws Exception{
//
//        when(articleService.getAllArticles()).thenReturn(new Article[0]);
//
//        mockMvc.perform(get("/qna/"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("qna/qnaList"))
//                .andExpect(model().attributeExists("articles"));
//
//        verify(articleService).getAllArticles();
//
//    }
//
//    @Test
//    @DisplayName("GET /question should return question input view")
//    void testGetQuestionForm() throws Exception{
//        mockMvc.perform(get("/qna/question"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("qna/submitQuestion"));
//    }
//
//    @Test
//    @DisplayName("POST /question should submit question article and redirect")
//    void testPostQuestionForm() throws Exception{
//
//        mockMvc.perform(post("/qna/question").param("title","mockTitle").param("content","mockContent"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:"));
//
//        verify(articleService).putNewArticle(any(Article.class));
//    }
//
//
//    @Test
//    @DisplayName("GET /{id} should show the detailed question post")
//    void testGetIndividualArticleById() throws Exception{
//
//        int articleId = 1;
//        Article mockArticle = new Article("mockTitle", "mockContent");
//        mockArticle.setId(articleId);
//        when(articleService.findArticleById(articleId)).thenReturn(mockArticle);
//
//        mockMvc.perform(get("/qna/"+articleId))
//                .andExpect(status().isOk())
//                .andExpect(view().name("qna/questionDetail"))
//                .andExpect(model().attributeExists("article"))
//                .andExpect(model().attribute("article",mockArticle));
//
//        verify(articleService).findArticleById(articleId);
//    }
//
//}
