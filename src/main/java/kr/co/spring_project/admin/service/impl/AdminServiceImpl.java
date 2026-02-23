package kr.co.spring_project.admin.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.spring_project.admin.service.AdminService;
import kr.co.spring_project.attendance.entity.Attendance;
import kr.co.spring_project.attendance.repository.AttendanceRepository;
import kr.co.spring_project.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final MemberRepository memberRepository;
    private final AttendanceRepository attendanceRepository;

    @Override
    public long getTotalMemberCount() {
        return memberRepository.count();
    }

    @Override
    public long getTodayCheckInCount() {
        LocalDate today = LocalDate.now();
        return attendanceRepository.findByCheckInBetween(
            today.atStartOfDay(),
            today.plusDays(1).atStartOfDay()
        ).size();
    }

    @Override
    public long getTodayAbsentCount() {
        return getTotalMemberCount() - getTodayCheckInCount();
    }

    @Override
    public long getTodayLateCount() {
        LocalDate today = LocalDate.now();
        return attendanceRepository.findByCheckInBetween(
            today.atStartOfDay(),
            today.plusDays(1).atStartOfDay()
        ).stream()
        .filter(a -> "지각".equals(a.getStatus()))
        .count();
    }

    @Override
    public int getTodayCheckInRate() {
        long total = getTotalMemberCount();
        if (total == 0) return 0;
        return (int) ((getTodayCheckInCount() * 100) / total);
    }

    @Override
    public List<Attendance> getRecentAttendances() {
        LocalDate today = LocalDate.now();
        return attendanceRepository.findByCheckInBetween(
            today.atStartOfDay(),
            today.plusDays(1).atStartOfDay()
        );
    }
}