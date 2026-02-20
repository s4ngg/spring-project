package kr.co.spring_project.attendance.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kr.co.spring_project.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attendance {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendanceId;
	
	// Member와 N:1 관계 (FK)
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    
	private String status;
    private LocalDateTime checkIn;   
    private LocalDateTime checkOut;
}