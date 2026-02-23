package kr.co.spring_project.admin.service;

import java.util.List;
import kr.co.spring_project.attendance.entity.Attendance;

public interface AdminService {
    long getTotalMemberCount();
    long getTodayCheckInCount();
    long getTodayAbsentCount();
    long getTodayLateCount();
    int getTodayCheckInRate();
    List<Attendance> getRecentAttendances();
}