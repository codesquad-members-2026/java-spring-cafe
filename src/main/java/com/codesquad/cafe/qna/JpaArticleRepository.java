package com.codesquad.cafe.qna;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT a FROM Article a JOIN FETCH a.writer")
    List<Article> findAllWithWriter();

    @Query("SELECT a FROM Article a JOIN FETCH a.writer WHERE a.id = :id")
    Optional<Article> findArticleWithWriterById(@Param("id") Long articleId);
}
