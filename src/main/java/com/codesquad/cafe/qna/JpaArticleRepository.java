package com.codesquad.cafe.qna;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaArticleRepository extends JpaRepository<Article, Long> {

}
