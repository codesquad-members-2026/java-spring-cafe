package com.codesquad.cafe.qna.dto;

import com.codesquad.cafe.qna.Comment;

import java.time.format.DateTimeFormatter;

public class CommentDetailsDTO {
    private Long commentId;
    private String contents;
    private String createdTime;
    private String writerLoginId;
    private Long articleId;

    // TODO: 생성자 코드에서 이 정도 로직은 괜찮은 건가?
    public CommentDetailsDTO(Comment comment) {
        this.commentId = comment.getId();
        this.contents = comment.getContents();
        this.createdTime = comment.getCreatedTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.writerLoginId = comment.getWriter().getLoginId();
        this.articleId = comment.getArticle().getId();
    }
}
