package com.study.board.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "board")
@EntityListeners(AuditingEntityListener.class)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String content;

    private String filename;

    private String filepath;

    @CreatedDate
    @Column(name = "createdTime", updatable = false)
    private LocalDateTime createdTime;

    @LastModifiedDate
    @Column(name = "updatedTime", updatable = false)
    private LocalDateTime updatedTime;

    @Builder
    public Board(Integer id, String title, String content, String filename, String filepath,
                 LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.filename = filename;
        this.filepath = filepath;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    public Board() {

    }
}
