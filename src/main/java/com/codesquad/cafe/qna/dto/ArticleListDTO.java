package com.codesquad.cafe.qna.dto;

public class ArticleListDTO {
    private Long id;
    private String title;
    private String writerLoginId;

    public ArticleListDTO(Long id, String title, String writerLoginId) {
        this.id = id;
        this.title = title;
        this.writerLoginId = writerLoginId;
    }
}
