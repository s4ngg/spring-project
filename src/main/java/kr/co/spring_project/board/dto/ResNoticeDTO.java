package kr.co.spring_project.board.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResNoticeDTO {

    private Long boardId;
    private String title;
    private String content;
    private String writerName;
    private String type;
    private boolean isPinned;
    private boolean isImportant;
    private Integer viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ResNoticeFileDTO> files;

    @Getter
    @Builder
    public static class ResNoticeFileDTO {
        private Long fileId;
        private String originalName;
        private String filePath;
        private String fileSize;
    }
}