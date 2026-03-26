package com.codesquad.cafe.exception;

public class ArticleInfoCannnotBeFoundException extends RuntimeException {
    public ArticleInfoCannnotBeFoundException(String msg){
        super(msg);
    }
}
