package kr.co.spring_project;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import kr.co.spring_project.attendance.entity.Attendance;
import kr.co.spring_project.attendance.service.AttendanceService;
import kr.co.spring_project.member.dto.ResloginDTO;
import kr.co.spring_project.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final AttendanceService attendanceService;
    private final ScheduleService scheduleService;

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        ResloginDTO loginMember = (ResloginDTO) session.getAttribute("LOGIN_MEMBER");
        if (loginMember != null) {
            Attendance todayAttendance =
                attendanceService.getTodayAttendance(loginMember.getEmployeeNo());
            model.addAttribute("todayAttendance", todayAttendance);

            try {
                List<Integer> scheduleDays = scheduleService.getScheduleList(session)
                    .stream()
                    .filter(s -> s.getStartDt() != null &&
                        s.getStartDt().getMonthValue() == java.time.LocalDate.now().getMonthValue())
                    .map(s -> s.getStartDt().getDayOfMonth())
                    .distinct()
                    .collect(Collectors.toList());
                model.addAttribute("scheduleDays", scheduleDays);
            } catch (Exception e) {
                model.addAttribute("scheduleDays", List.of());
            }
        }
        model.addAttribute("today", java.time.LocalDate.now());
        return "pages/home/index";
    }
}