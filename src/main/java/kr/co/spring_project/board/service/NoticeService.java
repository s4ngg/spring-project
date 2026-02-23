package kr.co.spring_project.board.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import kr.co.spring_project.board.dto.ReqNoticeDTO;
import kr.co.spring_project.board.dto.ResNoticeDTO;
import kr.co.spring_project.member.dto.ResloginDTO;

public interface NoticeService {

    Page<ResNoticeDTO> getNoticeList(int page, String keyword, String type);

    ResNoticeDTO getNoticeDetail(Long boardId);

    void saveNotice(ReqNoticeDTO dto, ResloginDTO writer, List<MultipartFile> files);

    void updateNotice(ReqNoticeDTO dto, List<MultipartFile> files);

    void deleteNotice(Long boardId);
}