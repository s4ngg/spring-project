package kr.co.spring_project.header.repository;

import kr.co.spring_project.header.entity.Message;
import kr.co.spring_project.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByReceiverAndReceiverDeletedFalseOrderBySentAtDesc(Member receiver);

    List<Message> findBySenderAndSenderDeletedFalseOrderBySentAtDesc(Member sender);

    long countByReceiverAndIsReadFalseAndReceiverDeletedFalse(Member receiver);

    @Query("SELECT m FROM Message m " +
           "JOIN FETCH m.sender " +
           "JOIN FETCH m.receiver " +
           "LEFT JOIN FETCH m.parentMessage " +
           "WHERE m.messageId = :id")
    Optional<Message> findByIdWithMembers(@Param("id") Long id);
}
