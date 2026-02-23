package kr.co.spring_project.attendance.service;

import kr.co.spring_project.attendance.dto.ReqAttendanceDTO;
import kr.co.spring_project.attendance.entity.Attendance;

public interface AttendanceService {
    void checkIn(ReqAttendanceDTO dto);
    void checkOut(ReqAttendanceDTO dto);
    Attendance getTodayAttendance(Long employeeNo);  // 추가
}