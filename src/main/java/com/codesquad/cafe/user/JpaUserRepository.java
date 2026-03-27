package com.codesquad.cafe.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<User, Long> {

    // 로그인 아이디로 유저 찾기
    Optional<User> findByLoginId(String loginId);

    // 로그인 아이디 + 비밀번호로 유저 찾기
    Optional<User> findByLoginIdAndPassword(String loginId, String password);
}
