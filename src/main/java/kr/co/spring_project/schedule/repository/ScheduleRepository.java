package kr.co.spring_project.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import kr.co.spring_project.schedule.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

}