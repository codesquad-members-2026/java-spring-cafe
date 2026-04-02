package com.codesquad.cafe.user.dto;

public class LoginUser {
    private final Long id;
    private final String name;

    public LoginUser(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }
}
