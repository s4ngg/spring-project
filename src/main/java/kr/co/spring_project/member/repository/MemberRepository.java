package kr.co.spring_project.member.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.spring_project.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	
	// 이메일 중복체크
	boolean existsByEmail(String email);
	
	// 사원번호
	Member findByEmail(String email);
}

