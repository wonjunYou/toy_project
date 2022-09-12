package com.study.board.dto;

import com.study.board.entity.BoardEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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


    /* DTO -> Entity */
    public BoardEntity toEntity(){
        BoardEntity boardEntity = BoardEntity.builder()
                .id(id)
                .title(title)
                .content(content)
                .createdTime(createdTime)
                .updatedTime(updatedTime)
                .build();

        return boardEntity;
    }

    @Builder
    public BoardDto(Integer id, String title, String content,
                    LocalDateTime createdTime, LocalDateTime updatedTime){
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }


    @Builder
    public BoardDto(BoardEntity boardEntity) {
        this.id = boardEntity.getId();
        this.title = boardEntity.getTitle();
        this.content = boardEntity.getContent();
        this.createdTime = boardEntity.getCreatedTime();
        this.updatedTime = boardEntity.getUpdatedTime();
    }

}
