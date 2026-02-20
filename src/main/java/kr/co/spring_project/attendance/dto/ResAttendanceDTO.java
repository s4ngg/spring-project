package kr.co.spring_project.attendance.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResAttendanceDTO {
    private Long attendanceId;
    private String status;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;

    // Member 연동 후 추가
    private Long memberId;
    private String memberName;
}