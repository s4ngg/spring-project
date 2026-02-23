package kr.co.spring_project.board.service;


	import java.util.List;

	import org.springframework.data.domain.Page;
	import org.springframework.web.multipart.MultipartFile;

	import kr.co.spring_project.board.dto.ReqBoardDTO;
	import kr.co.spring_project.board.dto.ResBoardDTO;

	public interface BoardService {

	    /**
	     * 게시글 작성
	     * @param request 사용자가 입력한 글쓰기 데이터
	     * @param files 첨부 파일 목록
	     * @param writerId 로그인한 회원의 ID
	     */
	    void write(ReqBoardDTO request, List<MultipartFile> files, Long writerId);

	    /**
	     * 게시글 목록 조회 (페이징)
	     */
	    Page<ResBoardDTO> getBoardList(int page);

	    /**
	     * 게시글 상세보기
	     * @param id 게시글 PK
	     */
	    ResBoardDTO getBoardDetail(Long id);

	    /**
	     * 게시글 수정 폼용 상세보기 (조회수 증가 없음)
	     * @param id 게시글 PK
	     */
	    ResBoardDTO getBoardDetailEdit(Long id);

	    /**
	     * 게시글 수정
	     * @param request 수정할 데이터
	     * @param id 로그인한 회원의 ID
	     */
	    void edit(ReqBoardDTO request, Long id);

	    /**
	     * 게시글 삭제
	     * @param id 게시글 PK
	     * @param loginUserId 로그인한 유저의 PK
	     */
	    void delete(Long id, Long loginUserId);
	}
