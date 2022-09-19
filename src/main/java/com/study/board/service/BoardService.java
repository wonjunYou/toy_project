package com.study.board.service;

import com.study.board.common.BeanConfiguration;
import com.study.board.dto.BoardDto;
import com.study.board.entity.BoardEntity;
import com.study.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    private ModelMapper modelMapper;
        //글 작성
    @Transactional
    public BoardEntity savePost(BoardDto boardDto) {

        BoardEntity boardEntity = modelMapper.map(boardDto, BoardEntity.class);

        return boardRepository.save(boardEntity);
    }

    // 게시글 목록 불러오기
    public Page<BoardDto> boardList(Pageable pageable){
        return boardRepository.findAll(pageable).map(BoardDto::new);
    }

    // 검색어를 포함하는 게시글만 불러오기
    public Page<BoardDto> boardSearchList(String searchKeyword, Pageable pageable) {

        return boardRepository.findByTitleContaining(searchKeyword, pageable).map(BoardDto::new);
    }

    // 게시글 상세 페이지
    public BoardDto boardView(Integer id){

        BoardEntity boardEntity = boardRepository.findById(id).get();

        BoardDto boardDto = new BoardDto(boardEntity);

        return boardDto;
    }

    // 특정 게시글 삭제
    public void boardDelete(Integer id) {

        boardRepository.deleteById(id);
    }


}
