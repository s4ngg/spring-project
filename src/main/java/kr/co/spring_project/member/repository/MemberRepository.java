package kr.co.spring_project.member.repository;

import java.lang.reflect.Member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>{
	
	// 이메일 중복체크
	boolean existsByEmail(String email);
	
	//
	Member findByUserId(String userId);
}

