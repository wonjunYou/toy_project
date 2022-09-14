package com.study.board.controller;

import com.study.board.dto.BoardDto;
import com.study.board.entity.BoardEntity;
import com.study.board.entity.FileEntity;
import com.study.board.repository.FileRepository;
import com.study.board.service.BoardService;
import com.study.board.service.FileService;

import lombok.RequiredArgsConstructor;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    private final FileService fileService;

    private final FileRepository fileRepository;

    @GetMapping("/board/writeform") //localhost:8080
    public String getBoardWriteForm(){
        return "boardwrite";
    }

    @PostMapping("/board/write")
    public String boardWrite(BoardDto boardDto, Model model,
                             @RequestParam("files") List<MultipartFile> files) throws Exception {

        BoardEntity boardEntity = boardService.savePost(boardDto);

        for (MultipartFile multipartFile : files) {
            fileService.saveFile(multipartFile, boardEntity);
        }

        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("redirectUrl", "/board");

        return "message";
    }

    @GetMapping("/board")
    public String getBoardList(Model model, @PageableDefault(page = 0, size = 10, sort ="id", direction = Sort.Direction.DESC)
                               Pageable pageable, String searchKeyword){

        Page<BoardDto> list;

        if (searchKeyword == null){
            list = boardService.boardList(pageable);
        } else {
            list = boardService.boardSearchList(searchKeyword, pageable);
        }

        // 검색 결과가 없는 경우
        if (list.getTotalElements() == 0){
            model.addAttribute("message", "검색 결과가 없습니다.");
            model.addAttribute("redirectUrl", "/board");

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
    public String boardView(Model model, Integer id) {
        BoardDto boardDto = boardService.boardView(id);

        List<FileEntity> files = fileRepository.findByBoardEntityId(id);

        model.addAttribute("boardDto", boardDto);
        model.addAttribute("all", files);
        return "boardview";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model) {

        BoardDto boardDto = boardService.boardView(id);
        model.addAttribute("boardDto", boardDto);

        return "boardmodify";
    }

    @PutMapping("/board/modify/{id}")
    public String boardUpdate(BoardDto boardDto,
                              @RequestParam("files") List<MultipartFile> files, BoardEntity boardEntity) throws Exception{

        boardService.savePost(boardDto);

        for (MultipartFile multipartFile : files) {
            fileService.saveFile(multipartFile, boardEntity);
        }

        //model.addAttribute("message", "글 수정이 완료되었습니다.");
        //model.addAttribute("redirectUrl", "/board");

        return "redirect:/board";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id){

        boardService.boardDelete(id);

        return "redirect:/board";
    }

    @GetMapping("/attach/{id}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Integer id) throws MalformedURLException {
        FileEntity file = fileRepository.findById(id).orElse(null);

        UrlResource resource = new UrlResource("file:" + file.getUplodeFilepath());
        String encodedFilename = UriUtils.encode(file.getOriginFilename(), StandardCharsets.UTF_8);

        // 파일 다운로드 대화상자가 뜨도록 헤더 설정
        String contentDisposition = "attachment; filename=\"" + encodedFilename + "\"";

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition).body(resource);
    }
}

