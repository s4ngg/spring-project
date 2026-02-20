package kr.co.spring_project.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter  // Getter 생성
@Setter // Setter 생성
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드가 다 있는 생성자
@ToString // 모든 필드에 있는 값을 문자열 형태로 반환
@Data 
public class ReqregisterDTO {
	private Long employeeNo;
	private String username;
	private String gender;
	private String email;
	private String password;
	private String passwordCheck;

}
