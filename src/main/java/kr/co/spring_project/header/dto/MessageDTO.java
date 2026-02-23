package kr.co.spring_project.header.dto;


import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.co.spring_project.header.entity.Message;
import kr.co.spring_project.member.entity.Member;
import lombok.Data;

public class MessageDTO {

    @Data
    public static class MemberSearchResult {
        private Long employeeNo;
        private String name;
        private String gradeName;
        private String deptName;

        public static MemberSearchResult from(Member m) {
            MemberSearchResult dto = new MemberSearchResult();
            dto.employeeNo = m.getEmployeeNo();
            dto.name       = m.getName();
            dto.gradeName  = m.getGradeName();
            dto.deptName   = (m.getDeptId() != null) ? m.getDeptId().getDeptName() : "";
            return dto;
        }
    }

    @Data
    public static class SendRequest {
        @NotNull(message = "수신자를 선택하세요.")
        private Long receiverEmployeeNo;

        @NotBlank(message = "제목을 입력하세요.")
        @Size(max = 200, message = "제목은 200자 이내로 입력하세요.")
        private String title;

        @NotBlank(message = "내용을 입력하세요.")
        private String content;

        private Long parentMessageId;
    }

    @Data
    public static class ListResponse {
        private Long messageId;
        private Long senderEmployeeNo;
        private String senderName;
        private String senderGrade;
        private Long receiverEmployeeNo;
        private String receiverName;
        private String title;
        private boolean isRead;
        private boolean hasParent;
        private LocalDateTime sentAt;

        public static ListResponse from(Message m) {
            ListResponse dto = new ListResponse();
            dto.messageId          = m.getMessageId();
            dto.senderEmployeeNo   = m.getSender().getEmployeeNo();
            dto.senderName         = m.getSender().getName();
            dto.senderGrade        = m.getSender().getGradeName();
            dto.receiverEmployeeNo = m.getReceiver().getEmployeeNo();
            dto.receiverName       = m.getReceiver().getName();
            dto.title              = m.getTitle();
            dto.isRead             = m.isRead();
            dto.hasParent          = m.getParentMessage() != null;
            dto.sentAt             = m.getSentAt();
            return dto;
        }
    }

    @Data
    public static class DetailResponse {
        private Long messageId;
        private Long senderEmployeeNo;
        private String senderName;
        private String senderGrade;
        private String senderDept;
        private Long receiverEmployeeNo;
        private String receiverName;
        private String title;
        private String content;
        private boolean isRead;
        private LocalDateTime sentAt;
        private Long parentMessageId;
        private String parentTitle;

        public static DetailResponse from(Message m) {
            DetailResponse dto = new DetailResponse();
            dto.messageId          = m.getMessageId();
            dto.senderEmployeeNo   = m.getSender().getEmployeeNo();
            dto.senderName         = m.getSender().getName();
            dto.senderGrade        = m.getSender().getGradeName();
            dto.senderDept         = (m.getSender().getDeptId() != null)
                                        ? m.getSender().getDeptName() : "";
            dto.receiverEmployeeNo = m.getReceiver().getEmployeeNo();
            dto.receiverName       = m.getReceiver().getName();
            dto.title              = m.getTitle();
            dto.content            = m.getContent();
            dto.isRead             = m.isRead();
            dto.sentAt             = m.getSentAt();
            if (m.getParentMessage() != null) {
                dto.parentMessageId = m.getParentMessage().getMessageId();
                dto.parentTitle     = m.getParentMessage().getTitle();
            }
            return dto;
        }
    }
}
