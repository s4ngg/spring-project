package kr.co.spring_project.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResBoardFileDTO {

    private Long id;                 // 파일 PK
    private String originalFileName; // 원본 파일명 (사용자가 올린 이름)
    private String storedFileName;   // 저장된 파일명 (UUID로 변환된 이름)
    private String filePath;         // 저장 경로
    private String contentType;      // 파일 형식 (image/jpeg 등)
    private Long fileSize;           // 파일 크기 (bytes)
}

