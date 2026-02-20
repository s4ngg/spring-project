package kr.co.spring_project.attendance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.spring_project.attendance.entity.Attendance;
import kr.co.spring_project.member.entity.Member;

public interface AttendanceRepository extends JpaRepository<Attendance, Long>{
	List<Attendance> findByMember(Member member);
}