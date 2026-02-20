package kr.co.spring_project.member.entity;

import java.time.LocalDate;
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
public class Member {
	@Id // PK(Primary Key) 지정
	@GeneratedValue(strategy = GenerationType.IDENTITY) //기본 키 자동 증가
	private Long employeeNo; // 수정
	private String name;
	private String gender;
	private String email;
	private String password;
	private String role;	
	
	private LocalDate joinDate;  // ← 추가
    private LocalDate leaveDate; // ← 추가 (nullable)
    
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	
	// INSERT 되기 직전에 자동 실행되는 어노테이션
	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.now();
		this.role = "USER"; 
	}
}
