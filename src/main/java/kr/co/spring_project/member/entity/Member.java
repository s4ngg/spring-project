package kr.co.spring_project.member.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder  // ← 추가
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeNo;
    
    private String name;
    private String gender;
    
    @Column(unique = true)
    private String email;
    
    private String password;
    private String role;
    private String gradeName;        // ← 추가
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id")
    private Department deptId;

    private LocalDate joinDate;
    private LocalDate leaveDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.role = "USER";
    }
}