package kr.co.spring_project.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.spring_project.board.entity.BoardFile;

public interface BoardFileRepository extends JpaRepository<BoardFile, Long>{
	
	List<BoardFile> findByBoardId(Long boardId);
}
