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
        Member member = Member.builder().employeeNo(dto.getEmployeeNo()).build();
        Attendance attendance = Attendance.builder()
                .member(member)
                .status(dto.getStatus())
                .checkOut(dto.getCheckTime())
                .build();
        attendanceRepository.save(attendance);
    }

    @Override
    public Attendance getTodayAttendance(Long employeeNo) {
        LocalDate today = LocalDate.now();
        List<Attendance> list = attendanceRepository
            .findByMember_EmployeeNoAndCheckInBetween(
                employeeNo,
                today.atStartOfDay(),
                today.plusDays(1).atStartOfDay()
            );
        return list.isEmpty() ? null : list.get(0);
    }
}