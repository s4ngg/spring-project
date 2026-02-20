package kr.co.spring_project.schedule.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ReqScheduleDTO {
    private String title;
    private String content;
    private LocalDateTime startDt;
    private LocalDateTime endAt;
    private Boolean isPublic;
}