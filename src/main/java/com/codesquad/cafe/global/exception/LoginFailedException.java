package com.codesquad.cafe.global.exception;

public class LoginFailedException extends RuntimeException {
    public LoginFailedException() {
        super("이메일 혹은 비밀번호가 잘못되었습니다.");
    }
}
