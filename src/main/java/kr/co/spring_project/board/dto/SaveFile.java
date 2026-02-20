package kr.co.spring_project.board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SaveFile {
	   private final String originalFileName;
	    private final String storedFileName;
	    private final String contentType;
	    private final Long fileSize;
	    private final String filePath;
}
