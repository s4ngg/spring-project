package kr.co.spring_project.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.spring_project.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
    int countByWriter_EmployeeNo(Long employeeNo);
    List<Board> findByWriter_EmployeeNoOrderByCreatedAtDesc(Long employeeNo);
}