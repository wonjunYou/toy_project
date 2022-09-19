package com.study.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FileDto {

    @Id
    @Column(nullable = false)
    private Integer file_id;

    private String originFilename;

    private String uplodeFilename;

    private String uplodeFilepath;

}
