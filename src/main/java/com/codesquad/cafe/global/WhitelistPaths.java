package com.codesquad.cafe.global;

public abstract class WhitelistPaths {

    public static final String[] STATIC_RESOURCES = {
            "/css/**", "/js/**", "/images/**", "/fonts/**",
            "/*.ico", "/error"
    };

    public static final String[] PUBLIC_PAGES = {
            "/users/login", "/user/form.html", "/users", "/questions"
    };
}