package kr.co.spring_project.attendance.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import kr.co.spring_project.attendance.dto.ReqAttendanceDTO;
import kr.co.spring_project.attendance.entity.Attendance;
import kr.co.spring_project.attendance.repository.AttendanceRepository;
import kr.co.spring_project.attendance.service.AttendanceService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;

    @Override
    public void checkIn(ReqAttendanceDTO dto) {
        Attendance attendance = Attendance.builder()
                .status(dto.getStatus())
                .checkIn(LocalDateTime.now())
                .build();
        attendanceRepository.save(attendance);
    }

    @Override
    public void checkOut(ReqAttendanceDTO dto) {
        Attendance attendance = Attendance.builder()
                .status(dto.getStatus())
                .checkOut(LocalDateTime.now())
                .build();
        attendanceRepository.save(attendance);
    }
}