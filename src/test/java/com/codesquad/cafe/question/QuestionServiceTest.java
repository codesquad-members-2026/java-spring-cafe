package com.codesquad.cafe.question;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QuestionServiceTest {
    private QuestionService questionService;
    private Question question1;
    private Question question2;


    @BeforeEach
    void init() {
        questionService = new QuestionService();

        question1 = new Question();
        question1.setTitle("hello");
        question1.setContents("world!");

        question2 = new Question();
        question2.setTitle("hello hello");
        question2.setContents("world world");
    }

    @Test
    @DisplayName("게시글을 작성하고 전체 조회할 수 있다")
    void saveAndGetAll() {
        // given
        questionService.save(loginUserId, question1);
        questionService.save(loginUserId, question2);

        // when
        List<Question> questions = questionService.getAll();

        // then
        assertThat(questions)
                .containsExactlyInAnyOrderElementsOf(List.of(question1, question2));
    }

    @Test
    @DisplayName("게시글을 작성하고 단건 조회할 수 있다")
    void saveAndGetById() {
        // given
        questionService.save(loginUserId, question1);

        // when
        Question found = questionService.get(1);

        // then
        assertThat(found).isEqualTo(question1);
    }
}