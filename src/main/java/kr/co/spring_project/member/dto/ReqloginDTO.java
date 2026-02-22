package kr.co.spring_project.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReqloginDTO {
	private Long employeeNo;
	private String email;
	private String password;
}
