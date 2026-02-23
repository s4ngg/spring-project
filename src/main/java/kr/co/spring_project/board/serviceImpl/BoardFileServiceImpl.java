package kr.co.spring_project.board.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import kr.co.spring_project.board.dto.ResBoardFileDTO;
import kr.co.spring_project.board.dto.SaveFile;
import kr.co.spring_project.board.entity.Board;
import kr.co.spring_project.board.entity.BoardFile;
import kr.co.spring_project.board.repository.BoardFileRepository;
import kr.co.spring_project.board.service.BoardFileService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardFileServiceImpl implements BoardFileService {
	private final BoardFileRepository boardFileRepository;
	
	private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/uploads/board";
	private static final String FILE_PATH_PREFIX = "/uploads/board/";
	
	@Override
	public List<ResBoardFileDTO> getFiles(Long boardId) {
		List<BoardFile> boardFiles = boardFileRepository.findByBoard_BoardId(boardId);
		
		List<ResBoardFileDTO> fileList = new ArrayList<>();
		
        for (BoardFile file : boardFiles) {
            ResBoardFileDTO response = ResBoardFileDTO.builder()
                    .id(file.getId())
                    .originalFileName(file.getOriginalFileName())
                    .storedFileName(file.getStoredFileName())
                    .filePath(file.getFilePath())
                    .fileSize(file.getFileSize())
                    .contentType(file.getContentType())
                    .build();
            fileList.add(response);
        }
        return fileList;
	}
	
    @Override
    @Transactional
    public void saveFiles(Board board, List<MultipartFile> files) {
        // 1. 업로드한 파일이 없으면 종료
        if (files == null || files.isEmpty()) return;

        // 2. 업로드 폴더가 없으면 생성
        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) dir.mkdirs();

        // 3. 파일 하나씩 처리
        for (MultipartFile file : files) {
            if (file == null || file.isEmpty()) continue;

            // 4. 서버에 파일 저장
            SaveFile saved = saveFileToDisk(file, dir);

            // 5. DB에 파일 정보 저장
            BoardFile boardFile = BoardFile.builder()
                    .board(board)
                    .originalFileName(saved.getOriginalFileName())
                    .storedFileName(saved.getStoredFileName())
                    .contentType(saved.getContentType())
                    .fileSize(saved.getFileSize())
                    .filePath(saved.getFilePath())
                    .build();

            boardFileRepository.save(boardFile);
        }
    }
        
        private SaveFile saveFileToDisk(MultipartFile file, File dir) {
            // 1. 원본 파일명 가져오기
            String originalFileName = file.getOriginalFilename();
            if (originalFileName == null || originalFileName.isBlank()) {
                originalFileName = "unknown";
            }

            // 2. 확장자 추출 (예: .jpg, .pdf)
            String ext = "";
            int dotIndex = originalFileName.lastIndexOf('.');
            if (dotIndex > -1) {
                ext = originalFileName.substring(dotIndex);
            }

            // 3. UUID로 저장 파일명 생성 (중복 방지)
            String storedFileName = UUID.randomUUID() + ext;

            // 4. 실제 파일을 서버에 저장
            File savedFile = new File(dir, storedFileName);
            try {
                file.transferTo(savedFile);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }

            // 5. 저장 결과 반환
            return SaveFile.builder()
                    .originalFileName(originalFileName)
                    .storedFileName(storedFileName)
                    .contentType(file.getContentType())
                    .fileSize(file.getSize())
                    .filePath(FILE_PATH_PREFIX + storedFileName)
                    .build();
        
    }
}
