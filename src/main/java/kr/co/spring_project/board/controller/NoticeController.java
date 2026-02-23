package kr.co.spring_project.board.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import kr.co.spring_project.board.dto.ReqNoticeDTO;
import kr.co.spring_project.board.dto.ResNoticeDTO;
import kr.co.spring_project.board.service.NoticeService;
import kr.co.spring_project.member.dto.ResloginDTO;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/board/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    // ── 목록 ──────────────────────────────────────────────────────
    @GetMapping
    public String list(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "keyword", defaultValue = "") String keyword,
            @RequestParam(name = "type", defaultValue = "") String type,
            Model model) {

        Page<ResNoticeDTO> result = noticeService.getNoticeList(page, keyword, type);

        int totalPages = result.getTotalPages() == 0 ? 1 : result.getTotalPages();
        int blockSize  = 5;
        int startPage  = ((page - 1) / blockSize) * blockSize + 1;
        int endPage    = Math.min(startPage + blockSize - 1, totalPages);

        model.addAttribute("list",        result.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages",  totalPages);
        model.addAttribute("startPage",   startPage);
        model.addAttribute("endPage",     endPage);
        model.addAttribute("keyword",     keyword);
        model.addAttribute("type",        type);

        return "pages/board/notice";
    }

    // ── 상세 ──────────────────────────────────────────────────────
    @GetMapping("/detail")
    public String detail(@RequestParam(name = "id") Long id, Model model) {
        ResNoticeDTO notice = noticeService.getNoticeDetail(id);
        model.addAttribute("notice", notice);
        return "pages/board/notice-detail";
    }

    // ── 작성 폼 ───────────────────────────────────────────────────
    @GetMapping("/write")
    public String writeForm(Model model, HttpSession session) {
        ResloginDTO loginMember = (ResloginDTO) session.getAttribute("LOGIN_MEMBER");
        if (loginMember == null || !"ADMIN".equals(loginMember.getRole())) {
            return "redirect:/board/notice";
        }
        model.addAttribute("boardDto", new ReqNoticeDTO());
        return "pages/board/notice-write";
    }

    // ── 작성 처리 ──────────────────────────────────────────────────
    @PostMapping("/write")
    public String write(
            @ModelAttribute ReqNoticeDTO dto,
            @RequestParam(name = "files", required = false) List<MultipartFile> files,
            HttpSession session) {

        ResloginDTO loginMember = (ResloginDTO) session.getAttribute("LOGIN_MEMBER");
        if (loginMember == null || !"ADMIN".equals(loginMember.getRole())) {
            return "redirect:/board/notice";
        }

        noticeService.saveNotice(dto, loginMember, files);
        return "redirect:/board/notice";
    }

    // ── 수정 폼 ───────────────────────────────────────────────────
    @GetMapping("/edit")
    public String editForm(@RequestParam(name = "id") Long id, Model model, HttpSession session) {
        ResloginDTO loginMember = (ResloginDTO) session.getAttribute("LOGIN_MEMBER");
        if (loginMember == null || !"ADMIN".equals(loginMember.getRole())) {
            return "redirect:/board/notice";
        }

        ResNoticeDTO notice = noticeService.getNoticeDetail(id);
        ReqNoticeDTO boardDto = new ReqNoticeDTO();
        boardDto.setBoardId(notice.getBoardId());
        boardDto.setTitle(notice.getTitle());
        boardDto.setContent(notice.getContent());
        boardDto.setType(notice.getType());

        model.addAttribute("notice",   notice);
        model.addAttribute("boardDto", boardDto);
        return "pages/board/notice-write";
    }

    // ── 수정 처리 ──────────────────────────────────────────────────
    @PostMapping("/edit")
    public String edit(
            @ModelAttribute ReqNoticeDTO dto,
            @RequestParam(name = "files", required = false) List<MultipartFile> files,
            HttpSession session) {

        ResloginDTO loginMember = (ResloginDTO) session.getAttribute("LOGIN_MEMBER");
        if (loginMember == null || !"ADMIN".equals(loginMember.getRole())) {
            return "redirect:/board/notice";
        }

        noticeService.updateNotice(dto, files);
        return "redirect:/board/notice/detail?id=" + dto.getBoardId();
    }

    // ── 삭제 처리 ──────────────────────────────────────────────────
    @PostMapping("/delete")
    public String delete(@RequestParam(name = "id") Long id, HttpSession session) {
        ResloginDTO loginMember = (ResloginDTO) session.getAttribute("LOGIN_MEMBER");
        if (loginMember == null || !"ADMIN".equals(loginMember.getRole())) {
            return "redirect:/board/notice";
        }

        noticeService.deleteNotice(id);
        return "redirect:/board/notice";
    }
}
