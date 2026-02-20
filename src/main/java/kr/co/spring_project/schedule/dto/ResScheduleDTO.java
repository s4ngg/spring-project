package kr.co.spring_project.schedule.dto;

import java.time.LocalDateTime;

import kr.co.spring_project.schedule.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResScheduleDTO {
    private Long scheduleId;
    private String title;
    private String content;
    private LocalDateTime startDt;
    private LocalDateTime endAt;
    private Boolean isPublic;
    private LocalDateTime createdAt;

    // Member 연동
    private Long memberId;
    private String memberName;
}