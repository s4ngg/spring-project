package kr.co.spring_project.header.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kr.co.spring_project.header.dto.MessageDTO;
import kr.co.spring_project.header.service.MessageService;
import kr.co.spring_project.member.entity.Member;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    private Member getLoginMember(HttpSession session) {
        Member member = (Member) session.getAttribute("LOGIN_MEMBER");
        if (member == null) throw new IllegalStateException("로그인이 필요합니다.");
        return member;
    }

    @GetMapping
    public String index() {
        return "redirect:/message/inbox";
    }

    @GetMapping("/inbox")
    public String inbox(HttpSession session, Model model) {
        Member login = getLoginMember(session);
        model.addAttribute("messages",    messageService.getInbox(login));
        model.addAttribute("unreadCount", messageService.countUnread(login));
        model.addAttribute("tab",         "inbox");
        return "message/list";
    }

    @GetMapping("/sent")
    public String sent(HttpSession session, Model model) {
        Member login = getLoginMember(session);
        model.addAttribute("messages",    messageService.getSentBox(login));
        model.addAttribute("unreadCount", messageService.countUnread(login));
        model.addAttribute("tab",         "sent");
        return "message/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, HttpSession session, Model model) {
        Member login = getLoginMember(session);
        model.addAttribute("message",     messageService.getDetail(id, login));
        model.addAttribute("unreadCount", messageService.countUnread(login));
        return "message/detail";
    }

    @GetMapping("/write")
    public String writeForm(@RequestParam(required = false) Long replyTo,
                            HttpSession session, Model model) {
        Member login = getLoginMember(session);
        MessageDTO.SendRequest form = new MessageDTO.SendRequest();
        if (replyTo != null) {
            MessageDTO.DetailResponse original = messageService.getDetail(replyTo, login);
            form.setParentMessageId(replyTo);
            form.setReceiverEmployeeNo(original.getSenderEmployeeNo());
            form.setTitle("Re: " + original.getTitle());
        }
        model.addAttribute("form",        form);
        model.addAttribute("unreadCount", messageService.countUnread(login));
        return "message/write";
    }

    // 수신자 이름 검색 AJAX
    @GetMapping("/search-receiver")
    @ResponseBody
    public ResponseEntity<List<MessageDTO.MemberSearchResult>> searchReceiver(
            @RequestParam String name, HttpSession session) {
        Member login = getLoginMember(session);
        return ResponseEntity.ok(messageService.searchReceiver(name, login.getEmployeeNo()));
    }

    @PostMapping("/send")
    public String send(@Valid @ModelAttribute("form") MessageDTO.SendRequest request,
                       BindingResult bindingResult,
                       HttpSession session,
                       RedirectAttributes ra, Model model) {
        Member login = getLoginMember(session);
        if (bindingResult.hasErrors()) {
            model.addAttribute("unreadCount", messageService.countUnread(login));
            return "message/write";
        }
        try {
            messageService.send(request, login);
            ra.addFlashAttribute("successMsg", "쪽지를 전송했습니다.");
            return "redirect:/message/sent";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMsg",    e.getMessage());
            model.addAttribute("unreadCount", messageService.countUnread(login));
            return "message/write";
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         @RequestParam(defaultValue = "inbox") String from,
                         HttpSession session, RedirectAttributes ra) {
        Member login = getLoginMember(session);
        try {
            messageService.delete(id, login);
            ra.addFlashAttribute("successMsg", "쪽지가 삭제되었습니다.");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMsg", e.getMessage());
        }
        return "redirect:/message/" + from;
    }
}
