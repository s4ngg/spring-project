package kr.co.spring_project.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity  //JPA 엔티티로 선언
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department {
	@Id // PK(Primary Key) 지정
	private String deptId;
	private String deptName;	
}
