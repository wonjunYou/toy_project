package com.study.board.service;

import com.study.board.dto.BoardDto;
import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired // DI
    private BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    //글 작성
    @Transactional
    public void savePost(BoardDto boardDto, MultipartFile file) throws Exception {

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files"; // 프로젝트의 경로를 담아준다.

        UUID uuid = UUID.randomUUID(); //랜덤으로 이름을 만들어준다.

        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);

        boardDto.setFilename(fileName);

        boardDto.setFilepath("/files/" + fileName);

        boardRepository.save(boardDto.toEntity()).getId();

        /*
        board.setFilename(fileName);

        board.setFilepath("/files/" + fileName);

        boardRepository.save(board);
        */
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

        Board board = boardRepository.findById(id).get();

        BoardDto boardDto = new BoardDto(board);

        return boardDto;

        /*
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException());
        return new BoardDto(board);
        */

    }

    // 특정 게시글 삭제
    public void boardDelete(Integer id) {

        boardRepository.deleteById(id);
    }
}
