package kr.co.spring_project.member.service.impl;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.spring_project.member.dto.ReqloginDTO;
import kr.co.spring_project.member.dto.ReqregisterDTO;
import kr.co.spring_project.member.dto.ResloginDTO;
import kr.co.spring_project.member.entity.Member;
import kr.co.spring_project.member.repository.MemberRepository;
import kr.co.spring_project.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
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
		member.setEmployee_no(request.getEmployee_no());
		member.setUsername(request.getUsername());
		member.setEmail(request.getEmail());
		member.setPassword(encodedPassword);
		// 강제 유저 부여
		member.setRole("USER");
		// 6. DB 저장
		memberRepository.save(member);
	}
	
	// 로그인처리
	@Override
	public ResloginDTO login(ReqloginDTO request) {
		Member member = memberRepository.findByEmail(request.getEmail());
		// 2. 존재하지 않으면 null 반환
		if(member == null){
			return null;
		}
		
		// 3. 사용자가 입력한 비밀번호가 암호화된 비밀번호와 일치하는지 검증
		//  - 일치하면 응답 객체 반환
		//  - 일치하지 않으면 null 반환
		
		if(!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
			return null;
		}
		
		ResloginDTO response = new ResloginDTO();
		response.setEmployee_no(member.getEmployee_no());
		response.setEmail(member.getEmail());
		response.setUsername(member.getUsername());
		response.setCreatedAt(member.getCreatedAt());
		response.setUpdatedAt(member.getUpdatedAt());
		
		return response;
	}
}
