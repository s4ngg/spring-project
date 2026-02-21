package kr.co.spring_project.member.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 마이페이지 '최근 활동' 목록에 표시되는 DTO
 * type        : 활동 구분 (예: 자료실, 공지, 결재 등)
 * description : 활동 내용 요약
 * createdAt   : 활동 일시
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityDTO {
    private String type;
    private String description;
    private LocalDateTime createdAt;
}