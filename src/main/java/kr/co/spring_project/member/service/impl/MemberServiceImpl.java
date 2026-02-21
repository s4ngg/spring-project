package kr.co.spring_project.member.service.impl;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kr.co.spring_project.member.dto.ActivityDTO;
import kr.co.spring_project.member.dto.MypageDTO;
import kr.co.spring_project.member.dto.ReqChangePasswordDTO;
import kr.co.spring_project.member.dto.ReqUpdateMemberDTO;
import kr.co.spring_project.member.dto.ReqloginDTO;
import kr.co.spring_project.member.dto.ReqregisterDTO;
import kr.co.spring_project.member.dto.ResloginDTO;
import kr.co.spring_project.member.entity.Member;
import kr.co.spring_project.member.repository.DepartmentRepository;
import kr.co.spring_project.member.repository.MemberRepository;
import kr.co.spring_project.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
	private final DepartmentRepository departmentRepository;
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
		    return;  // ← 추가
		}
		
		// 4. 비밀번호 암호화(Spring Security의 BCrypt 사용)
		String encodedPassword = passwordEncoder.encode(request.getPassword());
		
		// 5. Member 엔티티 생성
		Member member = new Member();
		member.setName(request.getName());
		member.setGender(request.getGender());
		member.setEmail(request.getEmail());
		member.setPassword(encodedPassword);
		member.setJoinDate(request.getJoinDate());   // ← 추가
		member.setLeaveDate(request.getLeaveDate()); // ← 추가
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
		response.setEmployeeNo(member.getEmployeeNo());
		response.setEmail(member.getEmail());
		response.setName(member.getName());
		response.setRole(member.getRole());
		response.setCreatedAt(member.getCreatedAt());
		response.setUpdatedAt(member.getUpdatedAt());

		return response;
	}
	
	// ── 마이페이지: 회원 조회 ─────────────────────────────────────────
    @Override
    public MypageDTO getMypage(Long employeeNo) {
        Member member = memberRepository.findById(employeeNo)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        return MypageDTO.builder()
                .employeeNo(member.getEmployeeNo())
                .name(member.getName())
                .email(member.getEmail())
                .gender(member.getGender())
                .role(member.getRole())
                .gradeName(member.getGradeName() != null ? member.getGradeName() : "-")
                // deptName: 현재 Member 엔티티에 deptName 없으므로 임시 "-"
                // 추후 Department 연관관계 설정 후 member.getDepartment().getName() 으로 교체
                .deptName("-")
                .joinDate(member.getJoinDate())
                .leaveDate(member.getLeaveDate())
                .build();
    }

    // ── 마이페이지: 이메일·성별 수정 ──────────────────────────────────
    @Override
    @Transactional
    public void updateMember(Long employeeNo, ReqUpdateMemberDTO request) {
        Member member = memberRepository.findById(employeeNo)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 이메일 빈값 체크
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("이메일을 입력해주세요.");
        }

        // 이메일 중복 체크 (본인 제외)
        if (!member.getEmail().equals(request.getEmail())
                && memberRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        member.setEmail(request.getEmail());
        member.setGender(request.getGender());
        // @Transactional 이므로 별도 save() 불필요 (dirty checking)
    }

    // ── 마이페이지: 비밀번호 변경 ─────────────────────────────────────
    @Override
    @Transactional
    public void changePassword(Long employeeNo, ReqChangePasswordDTO request) {
        Member member = memberRepository.findById(employeeNo)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        if (!passwordEncoder.matches(request.getCurrentPassword(), member.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 올바르지 않습니다.");
        }
        if (!request.getNewPassword().equals(request.getNewPasswordCheck())) {
            throw new IllegalArgumentException("새 비밀번호가 일치하지 않습니다.");
        }
        if (request.getNewPassword().length() < 8) {
            throw new IllegalArgumentException("새 비밀번호는 8자 이상이어야 합니다.");
        }

        member.setPassword(passwordEncoder.encode(request.getNewPassword()));
    }

    // ── 마이페이지: 근속 연수 ─────────────────────────────────────────
    @Override
    public double getWorkYears(Long employeeNo) {
        Member member = memberRepository.findById(employeeNo)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        if (member.getJoinDate() == null) return 0.0;

        LocalDate end = (member.getLeaveDate() != null) ? member.getLeaveDate() : LocalDate.now();
        long days = ChronoUnit.DAYS.between(member.getJoinDate(), end);
        // 소수점 1자리 반올림
        return Math.round((days / 365.0) * 10.0) / 10.0;
    }

    // ── 마이페이지: 잔여 연차 ─────────────────────────────────────────
    @Override
    public int getRemainVacation(Long employeeNo) {
        // TODO: 연차 테이블 연동 후 실제 값 반환
        return 15;
    }

    // ── 마이페이지: 게시글 수 ─────────────────────────────────────────
    @Override
    public int getPostCount(Long employeeNo) {
        // TODO: Post 테이블 연동 후 실제 count 반환
        return 0;
    }

    // ── 마이페이지: 최근 활동 ─────────────────────────────────────────
    @Override
    public List<ActivityDTO> getRecentActivities(Long employeeNo) {
        // TODO: 게시글/결재/자료실 등 실제 활동 데이터 조회 후 반환
        return Collections.emptyList();
    }
}
