package com.study.board.dto;

import com.study.board.entity.BoardEntity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDto {

    @Id
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;


    @Builder
    public BoardDto(BoardEntity boardEntity) {
        this.id = boardEntity.getId();
        this.title = boardEntity.getTitle();
        this.content = boardEntity.getContent();
        this.createdTime = boardEntity.getCreatedTime();
        this.updatedTime = boardEntity.getUpdatedTime();
    }

}
