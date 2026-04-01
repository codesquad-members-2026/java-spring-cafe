package com.codesquad.cafe.qna.dto;

import com.codesquad.cafe.qna.Article;
import com.codesquad.cafe.qna.Comment;
import com.codesquad.cafe.user.User;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class CommentWriteDTO {
    @NotBlank(message = "")
    private String contents;

    public CommentWriteDTO(String contents) {
        this.contents = contents;
    }

    public Comment toEntity(LocalDateTime createdTime, User sessionUser, Article article) {
        return new Comment(contents, createdTime, sessionUser, article);
    }
}
