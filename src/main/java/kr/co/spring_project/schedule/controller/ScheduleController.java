package kr.co.spring_project.schedule.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.spring_project.schedule.dto.ReqScheduleDTO;
import kr.co.spring_project.schedule.dto.ResScheduleDTO;
import kr.co.spring_project.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 일정 목록 페이지
    @GetMapping("")
    public String schedulePage(Model model) {
        List<ResScheduleDTO> list = scheduleService.getScheduleList();
        model.addAttribute("list", list);
        return "pages/home/schedule"; 
    }

    // 일정 등록
    @PostMapping("/create")
    public String createSchedule(ReqScheduleDTO dto) {
        scheduleService.createSchedule(dto);
        return "redirect:/schedule";
    }

    // 일정 상세
    @GetMapping("/{scheduleId}")
    public String getSchedule(@PathVariable Long scheduleId, Model model) {
        ResScheduleDTO schedule = scheduleService.getSchedule(scheduleId);
        model.addAttribute("schedule", schedule);
        return "pages/home/schedule-detail";
    }

    // 일정 수정
    @PostMapping("/update/{scheduleId}")
    public String updateSchedule(@PathVariable Long scheduleId, ReqScheduleDTO dto) {
        scheduleService.updateSchedule(scheduleId, dto);
        return "redirect:/schedule";
    }

    // 일정 삭제
    @PostMapping("/delete/{scheduleId}")
    public String deleteSchedule(@PathVariable Long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
        return "redirect:/schedule";
    }
}