package kr.co.spring_project.member.service;

import java.util.List;

import kr.co.spring_project.member.dto.ActivityDTO;
import kr.co.spring_project.member.dto.MypageDTO;
import kr.co.spring_project.member.dto.ReqChangePasswordDTO;
import kr.co.spring_project.member.dto.ReqUpdateMemberDTO;
import kr.co.spring_project.member.dto.ReqloginDTO;
import kr.co.spring_project.member.dto.ReqregisterDTO;
import kr.co.spring_project.member.dto.ResloginDTO;

public interface MemberService {
	// 회원가입
	void register(ReqregisterDTO request);
	
	// 로그인처리
	ResloginDTO login(ReqloginDTO request);

	// ── 마이페이지 ────────────────────────────
    /** 로그인된 회원 번호로 마이페이지 DTO 조회 */
    MypageDTO getMypage(Long employeeNo);

    /** 이메일·성별 수정 */
    void updateMember(Long employeeNo, ReqUpdateMemberDTO request);

    /** 비밀번호 변경 (현재 비밀번호 검증 포함) */
    void changePassword(Long employeeNo, ReqChangePasswordDTO request);

    /** 근속 연수 (소수점 1자리) */
    double getWorkYears(Long employeeNo);

    /** 잔여 연차 (임시: 15 고정, 추후 연차 테이블 연동) */
    int getRemainVacation(Long employeeNo);

    /** 작성 게시글 수 (임시: 0 고정, 추후 Post 테이블 연동) */
    int getPostCount(Long employeeNo);

    /** 최근 활동 목록 (임시: 빈 리스트, 추후 실제 데이터 연동) */
    List<ActivityDTO> getRecentActivities(Long employeeNo);
}







