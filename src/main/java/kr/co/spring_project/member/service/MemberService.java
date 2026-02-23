package kr.co.spring_project.member.service;

import kr.co.spring_project.member.dto.ReqloginDTO;
import kr.co.spring_project.member.dto.ReqregisterDTO;
import kr.co.spring_project.member.dto.ResloginDTO;

public interface MemberService {
	// 회원가입
	void register(ReqregisterDTO request);
	
	// 로그인처리
	ResloginDTO login(ReqloginDTO request);

}
