package kr.co.spring_project;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import kr.co.spring_project.attendance.entity.Attendance;
import kr.co.spring_project.attendance.service.AttendanceService;
import kr.co.spring_project.member.dto.ResloginDTO;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final AttendanceService attendanceService;

    @GetMapping("/")
    public String goHome(Model model, HttpSession session) {
        model.addAttribute("today", java.time.LocalDate.now());

        ResloginDTO loginMember = (ResloginDTO) session.getAttribute("LOGIN_MEMBER");
        if (loginMember != null) {
            Attendance todayAttendance = attendanceService.getTodayAttendance(loginMember.getEmployeeNo());
            model.addAttribute("todayAttendance", todayAttendance);
        }

        return "pages/home/index";
    }
}