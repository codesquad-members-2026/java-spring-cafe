package com.codesquad.cafe.exception;

public class CommentDoNotExistInTheArticleException extends RuntimeException {
    public CommentDoNotExistInTheArticleException(String message) {
        super(message);
    }
}
