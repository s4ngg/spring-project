package kr.co.spring_project.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 마이페이지 기본 정보 수정 요청 DTO (이메일, 성별)
 */
@Getter
@Setter
@NoArgsConstructor
public class ReqUpdateMemberDTO {
    private String email;
    private String gender;
}