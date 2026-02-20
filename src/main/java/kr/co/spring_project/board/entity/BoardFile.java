package kr.co.spring_project.board.entity;

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
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity           // JPA가 이 클래스를 DB 테이블로 인식
@Getter           // 모든 필드의 getter 자동 생성 (Lombok)
@NoArgsConstructor // 기본 생성자 자동 생성 → JPA 필수
@AllArgsConstructor // 전체 필드 생성자 자동 생성
@Builder          // builder 패턴으로 객체 생성 가능하게

public class BoardFile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id")
	private Board board;
	
	@Column(nullable = false, length = 255)
	private String originalFileName;
	
	@Column(nullable = false, length = 255, unique = true)
	private String storedFileName;
	
	@Column(length = 100)
	private String contentType;
	
	@Column(nullable = false)
	private Long fileSize;
	
	@Column(nullable = false)  // ✅ 이 부분 추가
	private String filePath;
	
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist  // INSERT 직전 자동 실행
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate   // UPDATE 직전 자동 실행
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
	
}
