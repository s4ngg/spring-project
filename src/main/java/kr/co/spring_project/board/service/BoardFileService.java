package kr.co.spring_project.board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import kr.co.spring_project.board.dto.ResBoardFileDTO;
import kr.co.spring_project.board.entity.Board;

public interface BoardFileService {
	void saveFiles(Board board, List<MultipartFile> files);
	
	List<ResBoardFileDTO> getFiles(Long boardId);
}
