package kr.co.spring_project.attendance.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.spring_project.attendance.entity.Attendance;
import kr.co.spring_project.board.entity.Board;

public interface AttendanceRepository extends JpaRepository<Attendance, Long>{
	List<Attendance> findByMember_EmployeeNoAndCheckInBetween(
		    Long employeeNo, LocalDateTime start, LocalDateTime end);

	List<Attendance> findByCheckInBetween(LocalDateTime start, LocalDateTime end);
}