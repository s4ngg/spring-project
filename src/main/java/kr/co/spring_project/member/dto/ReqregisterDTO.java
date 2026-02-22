package kr.co.spring_project.member.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class ReqregisterDTO {
    private String name;          // ← 추가
    private String gender;
    private String email;
    private String password;
    private String passwordCheck;
    private LocalDate joinDate;   // ← 추가
    private LocalDate leaveDate;  // ← 추가
}