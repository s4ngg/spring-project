package kr.co.spring_project.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqNoticeDTO {

    private Long boardId;
    private String title;
    private String content;
    private String type;
    private boolean isPinned;
    private boolean isImportant;
}