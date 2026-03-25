package com.codesquad.cafe.exception;

public class UserInfoCannotBeFoundException extends RuntimeException {
    public UserInfoCannotBeFoundException(String message) {
        super(message);
    }
}
