package kr.co.spring_project.attendance.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.spring_project.attendance.dto.ReqAttendanceDTO;
import kr.co.spring_project.attendance.service.AttendanceService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    // 출근 체크
    @PostMapping("/check-in")
    public String checkIn() {
        ReqAttendanceDTO dto = ReqAttendanceDTO.builder()
                .status("정상")
                .build();
        attendanceService.checkIn(dto);
        return "redirect:/";
    }

    // 퇴근 체크
    @PostMapping("/check-out")
    public String checkOut() {
        ReqAttendanceDTO dto = ReqAttendanceDTO.builder()
                .status("정상")
                .build();
        attendanceService.checkOut(dto);
        return "redirect:/";
    }
}