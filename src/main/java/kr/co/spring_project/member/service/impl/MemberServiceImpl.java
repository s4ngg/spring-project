package kr.co.spring_project.member.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import kr.co.spring_project.member.dto.ReqloginDTO;
import kr.co.spring_project.member.dto.ReqregisterDTO;
import kr.co.spring_project.member.dto.ResloginDTO;
import kr.co.spring_project.member.repository.MemberRepository;
import kr.co.spring_project.member.service.MemberService;
import kr.co.study.member.entity.Member;

public class MemberServiceImpl implements MemberService{
	
	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	
	// 회원가입처리
	@Override
	public void register(ReqregisterDTO request) {
		// 1. 비밀번호 & 비밀번호 확인 검증
		if(!request.getPassword().equals(request.getPasswordCheck())){
			System.out.println("비밀번호가 일치하지 않습니다.");
		}
		
		// 2. 이메일 중복 체크
		if(memberRepository.existsByEmail(request.getEmail())) {
			System.out.println("이미 사용중인 이메일 입니다.");
		}
		
		// 4. 비밀번호 암호화(Spring Security의 BCrypt 사용)
		String encodedPassword = passwordEncoder.encode(request.getPassword());
		
		// 5. Member 엔티티 생성
		Member member = new Member();
		member.setUserId(request.getUserId());
		member.setUserName(request.getUserName());
		member.setEmail(request.getEmail());
		member.setPassword(encodedPassword);
		
		// 6. DB 저장
		memberRepository.save(member);
	}
	
	// 로그인처리
	ResloginDTO login(ReqloginDTO request);
}
