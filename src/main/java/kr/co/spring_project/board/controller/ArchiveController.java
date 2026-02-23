package kr.co.spring_project.board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board/archive")
public class ArchiveController {

	@GetMapping
	public String archive(Model model) {

	    model.addAttribute("list", List.of());   // 비어있는 리스트라도 넣어
	    model.addAttribute("currentPage", 1);
	    model.addAttribute("totalPages", 1);
	    model.addAttribute("startPage", 1);
	    model.addAttribute("endPage", 1);

	    return "pages/archive/archive";
	}
}