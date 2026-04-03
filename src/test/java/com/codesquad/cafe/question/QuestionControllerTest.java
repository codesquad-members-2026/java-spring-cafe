package com.codesquad.cafe.question;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(QuestionController.class)
public class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private QuestionService questionService;

    @Test
    @DisplayName("전체 게시글 목록을 조회할 수 있다")
    void getQuestions() throws Exception {
        mockMvc.perform(get("/questions"))
                .andExpect(status().isOk())
                .andExpect(view().name("questions"));
    }

    @Test
    @DisplayName("전체 게시글 목록을 조회할 수 있다")
    void getQuestionById() throws Exception {
        // given
        Question question = new Question();
        question.setTitle("hello");
        question.setContent("world");

        given(questionService.get(1)).willReturn(question);

        mockMvc.perform(get("/questions/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("questions-detail"));
    }

    @Test
    @DisplayName("게시글을 작성 후 목록으로 리다이렉트 된다")
    void create() throws Exception{
        mockMvc.perform(post("/questions")
                        .param("title", "hello")
                        .param("contents", "world!"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/questions"));
    }

}
