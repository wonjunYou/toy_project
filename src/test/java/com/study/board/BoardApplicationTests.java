package com.study.board;

import com.study.board.entity.BoardEntity;
import com.study.board.entity.FileEntity;
import com.study.board.repository.BoardRepository;
import com.study.board.repository.FileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
class BoardApplicationTests {

	@Autowired
	BoardRepository boardRepository;

	@Autowired
	FileRepository fileRepository;

	@Test
	public void contextLoads() {
	}

}
