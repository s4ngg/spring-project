package kr.co.spring_project.attendance.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.spring_project.attendance.dto.ReqAttendanceDTO;
import kr.co.spring_project.attendance.entity.Attendance;
import kr.co.spring_project.attendance.repository.AttendanceRepository;
import kr.co.spring_project.attendance.service.AttendanceService;
import kr.co.spring_project.member.entity.Member;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;

    @Override
    public void checkIn(ReqAttendanceDTO dto) {
        // 오늘 이미 출근한 기록 있으면 막기
        LocalDate today = LocalDate.now();
        List<Attendance> existing = attendanceRepository
            .findByMember_EmployeeNoAndCheckInBetween(
                dto.getEmployeeNo(),
                today.atStartOfDay(),
                today.plusDays(1).atStartOfDay()
            );
        
        if (!existing.isEmpty()) {
            throw new RuntimeException("오늘 이미 출근 체크를 했습니다.");
        }

        Member member = Member.builder().employeeNo(dto.getEmployeeNo()).build();
        Attendance attendance = Attendance.builder()
                .member(member)
                .status(dto.getStatus())
                .checkIn(dto.getCheckTime())
                .build();
        attendanceRepository.save(attendance);
    }

    @Override
    public void checkOut(ReqAttendanceDTO dto) {

        // 1. 오늘 출근 기록 가져오기
        Attendance attendance = getTodayAttendance(dto.getEmployeeNo());

        if (attendance == null) {
            throw new RuntimeException("출근 기록이 없습니다.");
        }

        // 2. 이미 퇴근했는지 체크
        if (attendance.getCheckOut() != null) {
            throw new RuntimeException("이미 퇴근 처리되었습니다.");
        }

        // 3. 퇴근 시간 업데이트
        attendance.setCheckOut(dto.getCheckTime());

        // 상태도 같이 업데이트하고 싶으면
        attendance.setStatus(dto.getStatus());

        attendanceRepository.save(attendance);   // 🔥 update로 동작
    }

    @Override
    public Attendance getTodayAttendance(Long employeeNo) {
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.plusDays(1).atStartOfDay();

        List<Attendance> list = attendanceRepository
            .findByMember_EmployeeNoAndCheckInBetween(employeeNo, start, end);
        
        if (!list.isEmpty()) return list.get(0);

        // check_in이 없으면 check_out 기준으로 조회
        list = attendanceRepository
            .findByMember_EmployeeNoAndCheckOutBetween(employeeNo, start, end);
        
        return list.isEmpty() ? null : list.get(0);
    }
}