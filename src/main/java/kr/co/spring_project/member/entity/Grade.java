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
public class Grade {
	@Id // PK(Primary Key) 지정
	@GeneratedValue(strategy = GenerationType.IDENTITY) //기본 키 자동 증가
	private Long gradeId;
	private String gradeName;
}
