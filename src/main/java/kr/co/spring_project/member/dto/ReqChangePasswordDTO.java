package kr.co.spring_project.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 마이페이지 비밀번호 변경 요청 DTO
 */
@Getter
@Setter
@NoArgsConstructor
public class ReqChangePasswordDTO {
    private String currentPassword;
    private String newPassword;
    private String newPasswordCheck;
}