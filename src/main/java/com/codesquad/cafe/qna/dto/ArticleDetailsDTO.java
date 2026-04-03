package com.codesquad.cafe.qna.dto;

public class ArticleDetailsDTO {
    private Long id;
    private String title;
    private String contents;
    private String writerLoginId;
    private Long writerId;

    public ArticleDetailsDTO(Long id, String title, String contents,
                             String writerLoginId, Long writerId) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.writerLoginId = writerLoginId;
        this.writerId = writerId;
    }
}
