package com.codesquad.cafe.qna;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaCommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByArticle_Id(Long id);

    void deleteAllByArticle_Id(Long id);

    // TODO: clearAutomatically = true 로 인해 영속성 컨텍스트 텅 비어서 Service의 이후 로직이 DB에 반영되지 않음(더티체킹 X)
    // TODO: flushAutomatically = true를 사용하면 1차 캐싱을 무시하지 않고 하이버네이트의 최적화를 무시할 수 있음
    // TODO: 왜 벌크연산 이후에는 clearAutomatically = true를 무조건 실행해야 하는가? 왜 1차 캐싱 내용을 지워야만 하는가?
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Comment c SET c.isActivated = FALSE WHERE c.article.id = :articleId")
    void deactivatedCommentsByArticleId(@Param("articleId") Long articleId);
}
