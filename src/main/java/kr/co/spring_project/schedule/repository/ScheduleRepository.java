package kr.co.spring_project.schedule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.spring_project.schedule.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
	List<Schedule> findByMember_EmployeeNoOrderByCreatedAtDesc(Long employeeNo);
	List<Schedule> findByMemberEmployeeNo(Long employeeNo);
}