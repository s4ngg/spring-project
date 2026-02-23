package kr.co.spring_project.member.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import kr.co.spring_project.member.dto.ReqloginDTO;
import kr.co.spring_project.member.dto.ReqregisterDTO;
import kr.co.spring_project.member.dto.ResloginDTO;
import kr.co.spring_project.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입 페이지
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("memberDto", new ReqregisterDTO()); // ← 추가
        return "pages/member/register";
    }

    // 회원가입 처리
    @PostMapping("/register")
    public String register(ReqregisterDTO request) {
        memberService.register(request);
        return "redirect:/member/login";
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String loginForm() {
        return "pages/member/login";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(ReqloginDTO request, HttpSession session) {
        ResloginDTO response = memberService.login(request);

        if(response == null) {
            return "redirect:/member/login";
        }
        // LOGIN_USER → LOGIN_MEMBER 로 통일 ← 수정
        session.setAttribute("LOGIN_MEMBER", response);
        return "redirect:/";
    }

    // 로그아웃
    @PostMapping("/logout")  // GET → POST 로 변경 (sidebar form이 POST)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/member/login";
    }
}