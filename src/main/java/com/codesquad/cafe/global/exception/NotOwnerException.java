package com.codesquad.cafe.global.exception;

public class NotOwnerException extends RuntimeException {
    public NotOwnerException() {
        super("리소스의 소유자가 아닙니다.");
    }
}
