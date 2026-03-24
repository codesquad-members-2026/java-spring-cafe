package com.codesquad.cafe.repository;

import com.codesquad.cafe.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ArticleRepository extends JpaRepository<Article, Long> {
}