package kr.co.spring_project.schedule.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import kr.co.spring_project.member.dto.ResloginDTO;
import kr.co.spring_project.member.entity.Member;
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
    public void createSchedule(ReqScheduleDTO dto, HttpSession session) {

        // 1. 로그인 체크
        ResloginDTO loginMember = (ResloginDTO) session.getAttribute("LOGIN_MEMBER");
        if (loginMember == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }

        // 2. 시작일시 > 종료일시 검증
        if (dto.getStartDt().isAfter(dto.getEndAt())) {
            throw new RuntimeException("종료일시는 시작일시보다 늦어야 합니다.");
        }

        Schedule schedule = Schedule.builder()
                .member(Member.builder().employeeNo(loginMember.getEmployeeNo()).build())
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
    public List<ResScheduleDTO> getScheduleList(HttpSession session) {
        ResloginDTO loginMember = (ResloginDTO) session.getAttribute("LOGIN_MEMBER");
        if (loginMember == null) throw new RuntimeException("로그인이 필요합니다.");

        return scheduleRepository.findAll()
                .stream()
                .filter(s -> 
                    // 본인 일정이거나
                    s.getMember() != null && s.getMember().getEmployeeNo().equals(loginMember.getEmployeeNo())
                    // 또는 공개 일정
                    || Boolean.TRUE.equals(s.getIsPublic())
                )
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
                .employeeNo(s.getMember() != null ? s.getMember().getEmployeeNo() : null)
                .memberName(s.getMember() != null ? s.getMember().getName() : null)
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
    public void deleteSchedule(Long scheduleId, HttpSession session) {

        // 1. 로그인 체크
        ResloginDTO loginMember = 
            (ResloginDTO) session.getAttribute("LOGIN_MEMBER");
        if (loginMember == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }

        Schedule schedule = scheduleRepository.findById(scheduleId)
            .orElseThrow(() -> new RuntimeException("해당 일정이 없습니다."));

        // 2. 본인 or 관리자만 삭제 가능
        if (!schedule.getMember().getEmployeeNo().equals(loginMember.getEmployeeNo())
                && !loginMember.getRole().equals("ADMIN")) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }

        scheduleRepository.deleteById(scheduleId);
    }
}