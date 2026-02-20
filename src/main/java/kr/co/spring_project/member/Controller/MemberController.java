package kr.co.spring_project.member.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import kr.co.spring_project.member.dto.ReqloginDTO;
import kr.co.spring_project.member.dto.ReqregisterDTO;
import kr.co.spring_project.member.dto.ResloginDTO;
import kr.co.spring_project.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@Controller //이클레스를 bin으로 관리 / 컨트롤러임을 선언하는 어노테이션
@RequestMapping("/member") //URL 매핑(GET+POST)
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	
	
	// 회원가입
	@GetMapping("/register/form")
	public String registerFrom() {
		return "pages/member/register";
	}
	
	
	@PostMapping("/register")
	public String register(ReqregisterDTO request) {
		memberService.register(request);
		
		return "redirect:/member/login/form";
	}
	
	@GetMapping("/login/form")
	public String loginForm() {
		return "pages/member/login";
	}
	
	// 로그인을 실행했을때
	@PostMapping("/login")
	public String login(ReqloginDTO request, HttpSession session) {
		ResloginDTO response = memberService.login(request);
		
		//로그인 실패할 경우 회원가입 페이지로 이동
		if(response == null) {
			return "redirect:/member/register/form";
		}
		//로그인 성공할 경우
		session.setAttribute("LOGIN_USER", response);
			return "redirect:/"; // 메인 페이지로 이동
	}
	
	// 로그아웃 시
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
		session.invalidate(); // 세션 무효화
		return "redirect:/";
	}
}
