package kr.co.spring_project.header.service.impl;

import kr.co.spring_project.header.dto.MessageDTO;
import kr.co.spring_project.header.entity.Message;              // ✅ 올바른 import
import kr.co.spring_project.header.repository.MessageRepository;
import kr.co.spring_project.header.service.MessageService;
import kr.co.spring_project.member.entity.Member;
import kr.co.spring_project.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MemberRepository  memberRepository;

    @Override
    public List<MessageDTO.MemberSearchResult> searchReceiver(String name, Long senderEmployeeNo) {
        return memberRepository.findByNameContaining(name)
                .stream()
                .filter(m -> !m.getEmployeeNo().equals(senderEmployeeNo))
                .map(MessageDTO.MemberSearchResult::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void send(MessageDTO.SendRequest request, Member sender) {
        Member receiver = memberRepository.findById(request.getReceiverEmployeeNo())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 수신자입니다."));

        if (receiver.getEmployeeNo().equals(sender.getEmployeeNo())) {
            throw new IllegalArgumentException("자기 자신에게 쪽지를 보낼 수 없습니다.");
        }

        Message parentMessage = null;
        if (request.getParentMessageId() != null) {
            parentMessage = messageRepository.findById(request.getParentMessageId()).orElse(null);
        }

        messageRepository.save(
            Message.builder()
                   .sender(sender)
                   .receiver(receiver)
                   .title(request.getTitle())
                   .content(request.getContent())
                   .parentMessage(parentMessage)
                   .build()
        );
    }

    @Override
    public List<MessageDTO.ListResponse> getInbox(Member receiver) {
        return messageRepository
                .findByReceiverAndReceiverDeletedFalseOrderBySentAtDesc(receiver)
                .stream()
                .map(MessageDTO.ListResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<MessageDTO.ListResponse> getSentBox(Member sender) {
        return messageRepository
                .findBySenderAndSenderDeletedFalseOrderBySentAtDesc(sender)
                .stream()
                .map(MessageDTO.ListResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<MessageDTO.ListResponse> getRecentInbox(Member receiver, int limit) {
        return messageRepository
                .findByReceiverAndReceiverDeletedFalseOrderBySentAtDesc(receiver)
                .stream()
                .limit(limit)
                .map(MessageDTO.ListResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MessageDTO.DetailResponse getDetail(Long messageId, Member loginMember) {
        Message message = messageRepository.findByIdWithMembers(messageId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쪽지입니다."));

        boolean isReceiver = message.getReceiver().getEmployeeNo().equals(loginMember.getEmployeeNo());
        boolean isSender   = message.getSender().getEmployeeNo().equals(loginMember.getEmployeeNo());

        if (!isReceiver && !isSender) {
            throw new SecurityException("해당 쪽지에 접근 권한이 없습니다.");
        }

        if (isReceiver && !message.isRead()) {
            message.markAsRead();
        }

        return MessageDTO.DetailResponse.from(message);
    }

    @Override
    @Transactional
    public void delete(Long messageId, Member loginMember) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쪽지입니다."));

        boolean isReceiver = message.getReceiver().getEmployeeNo().equals(loginMember.getEmployeeNo());
        boolean isSender   = message.getSender().getEmployeeNo().equals(loginMember.getEmployeeNo());

        if (!isReceiver && !isSender) {
            throw new SecurityException("해당 쪽지에 접근 권한이 없습니다.");
        }

        if (isReceiver) message.deleteByReceiver();
        if (isSender)   message.deleteBySender();

        if (message.isSenderDeleted() && message.isReceiverDeleted()) {
            messageRepository.delete(message);
        }
    }

    @Override
    public long countUnread(Member member) {
        return messageRepository.countByReceiverAndIsReadFalseAndReceiverDeletedFalse(member);
    }
}