package kr.co.spring_project.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.spring_project.member.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, String>{

}
