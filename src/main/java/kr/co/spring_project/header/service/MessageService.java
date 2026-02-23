package kr.co.spring_project.header.service;

import kr.co.spring_project.header.dto.MessageDTO;
import kr.co.spring_project.member.entity.Member;

import java.util.List;

public interface MessageService {

    List<MessageDTO.MemberSearchResult> searchReceiver(String name, Long senderEmployeeNo);

    void send(MessageDTO.SendRequest request, Member sender);

    List<MessageDTO.ListResponse> getInbox(Member receiver);

    List<MessageDTO.ListResponse> getSentBox(Member sender);

    /** 팝업용 최근 받은 쪽지 N개 */
    List<MessageDTO.ListResponse> getRecentInbox(Member receiver, int limit);

    MessageDTO.DetailResponse getDetail(Long messageId, Member loginMember);

    void delete(Long messageId, Member loginMember);

    long countUnread(Member member);
}