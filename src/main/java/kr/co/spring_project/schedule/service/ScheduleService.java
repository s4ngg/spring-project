package kr.co.spring_project.schedule.service;

import java.util.List;
import kr.co.spring_project.schedule.dto.ReqScheduleDTO;
import kr.co.spring_project.schedule.dto.ResScheduleDTO;

public interface ScheduleService {

    // 일정 등록
    void createSchedule(ReqScheduleDTO dto);

    // 일정 전체 조회
    List<ResScheduleDTO> getScheduleList();

    // 일정 단건 조회
    ResScheduleDTO getSchedule(Long scheduleId);

    // 일정 수정
    void updateSchedule(Long scheduleId, ReqScheduleDTO dto);

    // 일정 삭제
    void deleteSchedule(Long scheduleId);
}