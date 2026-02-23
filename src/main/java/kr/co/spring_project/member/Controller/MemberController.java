package kr.co.spring_project.member.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import kr.co.spring_project.member.dto.MypageDTO;
import kr.co.spring_project.member.dto.ReqChangePasswordDTO;
import kr.co.spring_project.member.dto.ReqUpdateMemberDTO;
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
    
 // ── 마이페이지 ────────────────────────────────────────────────────
    /**
     * 마이페이지 조회
     * 세션의 LOGIN_MEMBER 에서 employeeNo 를 꺼내 각 정보를 Model 에 담아 반환
     */
    @GetMapping("/mypage")
    public String mypage(HttpSession session, Model model) {
        ResloginDTO loginMember = (ResloginDTO) session.getAttribute("LOGIN_MEMBER");
        if (loginMember == null) {
            return "redirect:/member/login";
        }

        Long employeeNo = loginMember.getEmployeeNo();

        MypageDTO member = memberService.getMypage(employeeNo);

        model.addAttribute("member", member);
        model.addAttribute("memberDto", new ReqUpdateMemberDTO()); // 수정 폼 바인딩용
        model.addAttribute("workYears", memberService.getWorkYears(employeeNo));
        model.addAttribute("remainVacation", memberService.getRemainVacation(employeeNo));
        model.addAttribute("postCount", memberService.getPostCount(employeeNo));
        model.addAttribute("recentActivities", memberService.getRecentActivities(employeeNo));

        return "pages/member/mypage";
    }

    /**
     * 기본 정보 수정 (이메일, 성별)
     */
    @PostMapping("/update")
    public String updateMember(@ModelAttribute ReqUpdateMemberDTO request,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        ResloginDTO loginMember = (ResloginDTO) session.getAttribute("LOGIN_MEMBER");
        if (loginMember == null) {
            return "redirect:/member/login";
        }

        try {
            memberService.updateMember(loginMember.getEmployeeNo(), request);
            // 세션의 이메일도 업데이트
            loginMember.setEmail(request.getEmail());
            session.setAttribute("LOGIN_MEMBER", loginMember);
            redirectAttributes.addFlashAttribute("successMessage", "정보가 수정되었습니다.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/member/mypage";
    }

    /**
     * 비밀번호 변경
     */
    @PostMapping("/change-password")
    public String changePassword(@ModelAttribute ReqChangePasswordDTO request,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        ResloginDTO loginMember = (ResloginDTO) session.getAttribute("LOGIN_MEMBER");
        if (loginMember == null) {
            return "redirect:/member/login";
        }

        try {
            memberService.changePassword(loginMember.getEmployeeNo(), request);
            redirectAttributes.addFlashAttribute("successMessage", "비밀번호가 변경되었습니다.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/member/mypage";
    }
}