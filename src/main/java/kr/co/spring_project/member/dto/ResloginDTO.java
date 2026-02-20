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
	private String employee_no;
	private String username;
	private String email;
	
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
