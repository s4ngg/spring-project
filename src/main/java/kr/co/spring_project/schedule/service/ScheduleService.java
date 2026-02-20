package kr.co.spring_project.schedule.service;

import java.util.List;
import jakarta.servlet.http.HttpSession;
import kr.co.spring_project.schedule.dto.ReqScheduleDTO;
import kr.co.spring_project.schedule.dto.ResScheduleDTO;

public interface ScheduleService {
    void createSchedule(ReqScheduleDTO dto, HttpSession session); // ← 수정
    List<ResScheduleDTO> getScheduleList(HttpSession session);
    ResScheduleDTO getSchedule(Long scheduleId);
    void updateSchedule(Long scheduleId, ReqScheduleDTO dto);
    void deleteSchedule(Long scheduleId, HttpSession session);
} 