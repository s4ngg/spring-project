
package kr.co.spring_project.board.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.spring_project.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findByBoardType(String boardType, Pageable pageable);

    Page<Board> findByBoardTypeAndTitleContainingOrBoardTypeAndContentContaining(
            String boardType1, String keyword1,
            String boardType2, String keyword2,
            Pageable pageable);

    Page<Board> findByBoardTypeAndCategory(String boardType, String category, Pageable pageable);
}