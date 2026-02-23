package kr.co.spring_project.board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import kr.co.spring_project.board.dto.ResBoardFileDTO;
import kr.co.spring_project.board.service.BoardFileService;
import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardFileService boardFileService;

    // ← noticeList 메서드 제거

    // 첨부파일 목록 조회
    @GetMapping("/files")
    public String getFiles(@RequestParam(name = "boardId") Long boardId,
                           Model model) {
        List<ResBoardFileDTO> files = boardFileService.getFiles(boardId);
        model.addAttribute("files", files);
        return "pages/board/notice-detail";
    }

    // 파일 업로드
    @PostMapping("/files/upload")
    public String uploadFiles(@RequestParam(name = "boardId") Long boardId,
                              @RequestParam(value = "files", required = false) List<MultipartFile> files,
                              HttpSession session) {
        Object loginUser = session.getAttribute("LOGIN_USER");
        if (loginUser == null) {
            return "redirect:/member/login/form";
        }
        return "redirect:/board/notice";
    }
}