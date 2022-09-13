package com.study.board.repository;

import com.study.board.entity.BoardEntity;
import com.study.board.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer> {
    List<FileEntity> findByBoardEntityId (int boardEntity_id);
}
