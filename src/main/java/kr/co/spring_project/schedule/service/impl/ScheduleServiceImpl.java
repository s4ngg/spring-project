package kr.co.spring_project.schedule.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kr.co.spring_project.schedule.dto.ReqScheduleDTO;
import kr.co.spring_project.schedule.dto.ResScheduleDTO;
import kr.co.spring_project.schedule.entity.Schedule;
import kr.co.spring_project.schedule.repository.ScheduleRepository;
import kr.co.spring_project.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    // 일정 등록
    @Override
    public void createSchedule(ReqScheduleDTO dto) {
        Schedule schedule = Schedule.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .startDt(dto.getStartDt())
                .endAt(dto.getEndAt())
                .isPublic(dto.getIsPublic())
                .createdAt(LocalDateTime.now())
                .build();
        scheduleRepository.save(schedule);
    }

    // 전체 조회
    @Override
    public List<ResScheduleDTO> getScheduleList() {
        return scheduleRepository.findAll()
                .stream()
                .map(s -> ResScheduleDTO.builder()
                        .scheduleId(s.getScheduleId())
                        .title(s.getTitle())
                        .content(s.getContent())
                        .startDt(s.getStartDt())
                        .endAt(s.getEndAt())
                        .isPublic(s.getIsPublic())
                        .createdAt(s.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    // 단건 조회
    @Override
    public ResScheduleDTO getSchedule(Long scheduleId) {
        Schedule s = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("해당 일정이 없습니다."));
        return ResScheduleDTO.builder()
                .scheduleId(s.getScheduleId())
                .title(s.getTitle())
                .content(s.getContent())
                .startDt(s.getStartDt())
                .endAt(s.getEndAt())
                .isPublic(s.getIsPublic())
                .createdAt(s.getCreatedAt())
                .build();
    }

    // 수정
    @Override
    public void updateSchedule(Long scheduleId, ReqScheduleDTO dto) {
        Schedule s = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("해당 일정이 없습니다."));
        s.setTitle(dto.getTitle());
        s.setContent(dto.getContent());
        s.setStartDt(dto.getStartDt());
        s.setEndAt(dto.getEndAt());
        s.setIsPublic(dto.getIsPublic());
        scheduleRepository.save(s);
    }

    // 삭제
    @Override
    public void deleteSchedule(Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }
}