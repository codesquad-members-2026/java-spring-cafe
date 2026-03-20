package com.codesquad.cafe.exception;

public class NoArticleInListException extends RuntimeException {
    public NoArticleInListException(String msg){
        super(msg);
    }
}
