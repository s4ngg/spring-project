package kr.co.spring_project.board.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import kr.co.spring_project.board.dto.ReqNoticeDTO;
import kr.co.spring_project.board.dto.ResNoticeDTO;
import kr.co.spring_project.board.dto.ResNoticeDTO.ResNoticeFileDTO;
import kr.co.spring_project.board.entity.Board;
import kr.co.spring_project.board.entity.BoardFile;
import kr.co.spring_project.board.repository.BoardFileRepository;
import kr.co.spring_project.board.repository.BoardRepository;
import kr.co.spring_project.board.service.BoardFileService;
import kr.co.spring_project.board.service.NoticeService;
import kr.co.spring_project.member.dto.ResloginDTO;
import kr.co.spring_project.member.entity.Member;
import kr.co.spring_project.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;
    private final BoardFileService boardFileService;
    private final MemberRepository memberRepository;

    private static final String BOARD_TYPE = "NOTICE";
    private static final int PAGE_SIZE = 10;

    @Override
    public Page<ResNoticeDTO> getNoticeList(int page, String keyword, String type) {
        Pageable pageable = PageRequest.of(page - 1, PAGE_SIZE,
                Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Board> boardPage;

        if (keyword != null && !keyword.isBlank()) {
            boardPage = boardRepository
                    .findByBoardTypeAndTitleContainingOrBoardTypeAndContentContaining(
                            BOARD_TYPE, keyword, BOARD_TYPE, keyword, pageable);
        } else if (type != null && !type.isBlank()) {
            boardPage = boardRepository.findByBoardTypeAndCategory(BOARD_TYPE, type, pageable);
        } else {
            boardPage = boardRepository.findByBoardType(BOARD_TYPE, pageable);
        }

        return boardPage.map(this::toListDTO);
    }

    @Override
    @Transactional
    public ResNoticeDTO getNoticeDetail(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공지입니다. id=" + boardId));

        board.setViewCount(board.getViewCount() == null ? 1 : board.getViewCount() + 1);

        List<BoardFile> boardFiles = boardFileRepository.findByBoardId(boardId);
        List<ResNoticeFileDTO> fileDTOs = boardFiles.stream()
                .map(f -> ResNoticeFileDTO.builder()
                        .fileId(f.getId())
                        .originalName(f.getOriginalFileName())
                        .filePath(f.getFilePath())
                        .fileSize(formatFileSize(f.getFileSize()))
                        .build())
                .collect(Collectors.toList());

        return toDetailDTO(board, fileDTOs);
    }

    @Override
    @Transactional
    public void saveNotice(ReqNoticeDTO dto, ResloginDTO writer, List<MultipartFile> files) {
        // ResloginDTO의 employeeNo로 실제 Member 엔티티 조회
        Member memberEntity = memberRepository.findById(writer.getEmployeeNo()).orElse(null);

        Board board = new Board();
        board.setBoardType(BOARD_TYPE);
        board.setTitle(dto.getTitle());
        board.setContent(dto.getContent());
        board.setCategory(dto.getType());
        board.setViewCount(0);
        board.setWriter(memberEntity);

        Board saved = boardRepository.save(board);
        boardFileService.saveFiles(saved, files);
    }

    @Override
    @Transactional
    public void updateNotice(ReqNoticeDTO dto, List<MultipartFile> files) {
        Board board = boardRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공지입니다. id=" + dto.getBoardId()));

        board.setTitle(dto.getTitle());
        board.setContent(dto.getContent());
        board.setCategory(dto.getType());

        if (files != null && !files.isEmpty()) {
            boardFileService.saveFiles(board, files);
        }
    }

    @Override
    @Transactional
    public void deleteNotice(Long boardId) {
        boardFileRepository.deleteAll(boardFileRepository.findByBoardId(boardId));
        boardRepository.deleteById(boardId);
    }

    private ResNoticeDTO toListDTO(Board board) {
        return ResNoticeDTO.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .writerName(board.getWriter() != null ? board.getWriter().getName() : "")
                .type(board.getCategory())
                .isImportant("IMPORTANT".equals(board.getCategory()))
                .viewCount(board.getViewCount())
                .createdAt(board.getCreatedAt())
                .build();
    }

    private ResNoticeDTO toDetailDTO(Board board, List<ResNoticeFileDTO> files) {
        return ResNoticeDTO.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .writerName(board.getWriter() != null ? board.getWriter().getName() : "")
                .type(board.getCategory())
                .isImportant("IMPORTANT".equals(board.getCategory()))
                .viewCount(board.getViewCount())
                .createdAt(board.getCreatedAt())
                .updatedAt(board.getUpdatedAt())
                .files(files)
                .build();
    }

    private String formatFileSize(Long bytes) {
        if (bytes == null) return "0 B";
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return String.format("%.1f KB", bytes / 1024.0);
        return String.format("%.1f MB", bytes / (1024.0 * 1024));
    }
}