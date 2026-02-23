package kr.co.spring_project.attendance.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import kr.co.spring_project.attendance.dto.ReqAttendanceDTO;
import kr.co.spring_project.attendance.entity.Attendance;
import kr.co.spring_project.attendance.service.AttendanceService;
import kr.co.spring_project.member.dto.ResloginDTO;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping("")
    public String attendancePage(Model model, HttpSession session) {

        ResloginDTO loginMember = (ResloginDTO) session.getAttribute("LOGIN_MEMBER");
        if (loginMember == null) return "redirect:/member/login";

        Attendance today = attendanceService.getTodayAttendance(loginMember.getEmployeeNo());

        model.addAttribute("today", today);

        return "pages/home/attendance";
    }

    @PostMapping("/check-in")
    public String checkIn(@RequestParam("checkTime") String checkTime,
                          @RequestParam("status") String status,
                          HttpSession session) {
        ResloginDTO loginMember = (ResloginDTO) session.getAttribute("LOGIN_MEMBER");
        if (loginMember == null) return "redirect:/member/login";

        LocalDate today = LocalDate.now();
        LocalTime time = LocalTime.parse(checkTime);
        LocalDateTime checkDateTime = LocalDateTime.of(today, time);

        try {
            ReqAttendanceDTO dto = ReqAttendanceDTO.builder()
                    .status(status)
                    .checkTime(checkDateTime)
                    .employeeNo(loginMember.getEmployeeNo())
                    .build();
            attendanceService.checkIn(dto);
        } catch (RuntimeException e) {
            return "redirect:/attendance?error=" + e.getMessage();
        }
        return "redirect:/attendance";
    }

    @PostMapping("/check-out")
    public String checkOut(@RequestParam("checkTime") String checkTime,
                           @RequestParam("status") String status,
                           HttpSession session) {
        ResloginDTO loginMember = (ResloginDTO) session.getAttribute("LOGIN_MEMBER");
        if (loginMember == null) return "redirect:/member/login";

        LocalDate today = LocalDate.now();
        LocalTime time = LocalTime.parse(checkTime);
        LocalDateTime checkDateTime = LocalDateTime.of(today, time);

        ReqAttendanceDTO dto = ReqAttendanceDTO.builder()
                .status(status)
                .checkTime(checkDateTime)
                .employeeNo(loginMember.getEmployeeNo())
                .build();
        attendanceService.checkOut(dto);
        return "redirect:/attendance";
    }
    
}