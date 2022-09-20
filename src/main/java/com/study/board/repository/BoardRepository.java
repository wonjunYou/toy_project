package com.study.board.repository;

import com.study.board.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

    Page<BoardEntity> findByTitleContaining(String searchKeyWord, Pageable pageable);

    @Modifying
    @Query("update BoardEntity boardEntity set boardEntity.viewCnt = boardEntity.viewCnt+1 where boardEntity.id = :id")
    int updateCount(@Param("id") Integer id);
}
