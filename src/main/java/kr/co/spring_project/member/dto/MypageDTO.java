package kr.co.spring_project.member.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 마이페이지 화면에 전달되는 회원 정보 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MypageDTO {

    private Long employeeNo;
    private String name;
    private String email;
    private String gender;
    private String role;
    private String gradeName;
    private String deptName;

    private LocalDate joinDate;
    private LocalDate leaveDate;
}