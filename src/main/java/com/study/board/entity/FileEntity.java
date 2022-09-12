package com.study.board.entity;


import com.study.board.dto.BoardDto;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "file")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer file_id;
    private String originFilename;
    private String uplodeFilename;
    private String uplodeFilepath;

    @Builder
    public FileEntity(String originFilename, String uplodeFilename, String uplodeFilepath){
        this.originFilename = originFilename;
        this.uplodeFilename = uplodeFilename;
        this.uplodeFilepath = uplodeFilepath;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="boardEntity_id")
    private BoardEntity boardEntity;
}
