package kr.co.spring_project.member.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResloginDTO {
    private Long employeeNo;        // String → Long 변경
    private String name;
    private String email;
    private String role;         
    private String gradeName;   
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    
}