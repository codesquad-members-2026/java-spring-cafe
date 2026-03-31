package com.codesquad.cafe.qna.dto;

import com.codesquad.cafe.qna.Article;
import com.codesquad.cafe.user.User;
import jakarta.validation.constraints.NotBlank;

public class ArticleWriteDTO {
    @NotBlank(message = "제목을 입력해야 합니다.")
    private String title;

    @NotBlank(message = "본문을 입력해야 합니다.")
    private String contents;

    public ArticleWriteDTO(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }
    public String getContents() {
        return contents;
    }

    public Article toEntity(User user) {
        return new Article(user, title, contents);
    }
}
