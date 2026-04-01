package com.codesquad.cafe.qna;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaCommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByArticle_Id(Long id);

    void deleteAllByArticle_Id(Long id);
}
