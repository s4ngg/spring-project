package kr.co.spring_project.board.dto;



import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResBoardDTO {
    private Long id;
    private String category;
    private String title;
    private String content;
    private String writerName;
    private LocalDateTime createdAt;
    private int viewCount;
    private List<ResBoardFileDTO> files;  // 첨부파일 목록
}