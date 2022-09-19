package com.study.board.service;

import com.study.board.dto.FileDto;
import com.study.board.entity.BoardEntity;
import com.study.board.entity.FileEntity;
import com.study.board.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.modelmapper.ModelMapper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class FileService {

    @Value("${file.dir}")
    private String fileDir;

    private final FileRepository fileRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Integer saveFile(MultipartFile files, BoardEntity boardEntity) throws IOException {

        if (files.isEmpty()) {
            return null;
        }

        String originalFilename = files.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();

        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));

        String uplodeFilename = uuid + ext;
        String uplodeFilepath = fileDir + uplodeFilename;

        // file entity 생성
        FileEntity file = FileEntity.builder()
                .originFilename(originalFilename)
                .uplodeFilename(uplodeFilename)
                .uplodeFilepath(uplodeFilepath)
                .build();

        // 로컬에 해당 파일명으로 저장
        files.transferTo(new File(uplodeFilepath));

        boardEntity.add(file);

        FileEntity uplodeFile = fileRepository.save(file);

        return uplodeFile.getFile_id();
    }

    public List<FileDto> getFiles(Integer id) {

        List<FileEntity> fileEntityList = fileRepository.findByBoardEntityId(id);
        List<FileDto> fileDtoList = fileEntityList.stream().map(file -> modelMapper.map(file, FileDto.class)).collect(Collectors.toList());

        return fileDtoList;
    }
}
