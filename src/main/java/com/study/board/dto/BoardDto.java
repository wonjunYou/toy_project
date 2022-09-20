package com.study.board.dto;

import com.study.board.entity.BoardEntity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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

    @Column(nullable = false, columnDefinition = "INT(11) default 0")
    private Integer viewCnt;


    @Builder
    public BoardDto(BoardEntity boardEntity) {
        this.id = boardEntity.getId();
        this.title = boardEntity.getTitle();
        this.content = boardEntity.getContent();
        this.createdTime = boardEntity.getCreatedTime();
        this.updatedTime = boardEntity.getUpdatedTime();
        this.viewCnt = boardEntity.getViewCnt();
    }

}
