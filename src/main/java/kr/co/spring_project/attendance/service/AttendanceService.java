package kr.co.spring_project.attendance.service;

import kr.co.spring_project.attendance.dto.ReqAttendanceDTO;

public interface AttendanceService {

	// 출근 체크
	void checkIn(ReqAttendanceDTO dto);

	// 퇴근 체크
	void checkOut(ReqAttendanceDTO dto);
}