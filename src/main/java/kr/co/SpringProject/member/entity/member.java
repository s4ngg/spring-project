package kr.co.SpringProject.member.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity  //JPA 엔티티로 선언
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class member {
	@Id // PK(Primary Key) 지정
	@GeneratedValue(strategy = GenerationType.IDENTITY) //기본 키 자동 증가
	private String employee_no;
	private String username;
	private String gender;
	private String email;
	private String password;
	
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	
	// INSERT 되기 직전에 자동 실행되는 어노테이션
	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.now();
	}
}
