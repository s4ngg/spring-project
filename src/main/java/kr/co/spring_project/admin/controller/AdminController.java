package kr.co.spring_project.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import kr.co.spring_project.admin.service.AdminService;
import kr.co.spring_project.member.dto.ResloginDTO;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("")
    public String adminDashboard(Model model, HttpSession session) {
        ResloginDTO loginMember = (ResloginDTO) session.getAttribute("LOGIN_MEMBER");
        if (loginMember == null || !"ADMIN".equals(loginMember.getRole())) {
            return "redirect:/";
        }

        model.addAttribute("totalMembers", adminService.getTotalMemberCount());
        model.addAttribute("todayCheckInCount", adminService.getTodayCheckInCount());
        model.addAttribute("todayAbsentCount", adminService.getTodayAbsentCount());
        model.addAttribute("todayLateCount", adminService.getTodayLateCount());
        model.addAttribute("checkInRate", adminService.getTodayCheckInRate());
        model.addAttribute("recentAttendances", adminService.getRecentAttendances());

        return "pages/admin/dashboard";
    }
}