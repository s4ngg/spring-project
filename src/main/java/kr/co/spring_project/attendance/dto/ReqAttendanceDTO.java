package kr.co.spring_project.attendance.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqAttendanceDTO {
    private String status;
    private LocalDateTime checkTime;
    private Long employeeNo;
}