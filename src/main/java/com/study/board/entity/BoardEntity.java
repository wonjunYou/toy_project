package com.study.board.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "board")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String content;

    @CreatedDate
    @Column(name = "createdTime", updatable = false)
    private LocalDateTime createdTime;

    @LastModifiedDate
    @Column(name = "updatedTime", updatable = false)
    private LocalDateTime updatedTime;


    @OneToMany(mappedBy = "boardEntity")
    private List<FileEntity> fileEntities = new ArrayList<FileEntity>();

    public void add(FileEntity fileEntity) {
        fileEntity.setBoardEntity(this);
        this.fileEntities.add(fileEntity);
    }
}
