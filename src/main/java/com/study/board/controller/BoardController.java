package com.study.board.controller;

import com.study.board.dto.BoardDto;
import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/board/writeform") //localhost:8080
    public String getBoardWriteForm(){
        return "boardwrite";
    }

    @PostMapping("/board/write")
    public String boardWrite(BoardDto boardDto, Model model, MultipartFile file) throws Exception {

        boardService.savePost(boardDto, file);

        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board");

        return "message";
    }

    @GetMapping("/board")
    public String getBoardList(Model model,
                            @PageableDefault(page = 0, size = 10, sort ="id", direction = Sort.Direction.DESC) Pageable pageable,
                            String searchKeyword){

        Page<BoardDto> list = null;

        if (searchKeyword == null){
            list = boardService.boardList(pageable);
        } else {
            list = boardService.boardSearchList(searchKeyword, pageable);
        }

        // 검색 결과가 없는 경우
        if (list.getTotalElements() == 0){
            model.addAttribute("message", "검색 결과가 없습니다.");
            model.addAttribute("searchUrl", "/board");

            return "message";
        }

        int nowPage = list.getPageable().getPageNumber() + 1; // 현재 페이지 pageable default index는 0이므로 +1을 함.
        int startPage = Math.max(nowPage - 4, 1); // 시작 페이지
        int endPage = Math.min(nowPage + 5, list.getTotalPages()); // 끝 페이지

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "boardlist";
    }

    @GetMapping("/board/view") // localhost:8080/board/view?id=1 get방식으로 인자 전달.
    public String boardView(Model model, Integer id){
        BoardDto boardDto = boardService.boardView(id);

        model.addAttribute("boardDto", boardDto);
        return "boardview";
    }


    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model) {

        BoardDto boardDto = boardService.boardView(id);
        model.addAttribute("boardDto", boardDto);

        return "boardmodify";
    }

    // 기존의 내용을 가져와서 새로운 내용으로 덮어 씌운다 -> 원래는 이렇게 하면 안됨 JPA의 변경 감지 학습!
    @PutMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, BoardDto boardDto, Model model, MultipartFile file) throws Exception {

        boardService.savePost(boardDto, file);

        model.addAttribute("message", "글 수정이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board");

        return "message";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id){

        boardService.boardDelete(id);

        return "redirect:/board/list";
    }
}

