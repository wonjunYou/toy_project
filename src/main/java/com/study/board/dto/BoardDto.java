package com.study.board.dto;

import com.study.board.entity.Board;
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
    private String filename;
    private String filepath;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;


    /* DTO -> Entity */
    public Board toEntity(){
        Board board = Board.builder()
                .id(id)
                .title(title)
                .content(content)
                .filename(filename)
                .filepath(filepath)
                .createdTime(createdTime)
                .updatedTime(updatedTime)
                .build();

        return board;
    }

    @Builder
    public BoardDto(Integer id, String title, String content, String filename, String filepath,
                    LocalDateTime createdTime, LocalDateTime updatedTime){
        this.id = id;
        this.title = title;
        this.content = content;
        this.filename = filename;
        this.filepath = filepath;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    @Builder
    public BoardDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.filename = board.getFilename();
        this.filepath = board.getFilepath();
        this.createdTime = board.getCreatedTime();
        this.updatedTime = board.getUpdatedTime();
    }

}
