package kr.co.spring_project.board.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

	public class ReqBoardDTO {
	    private Long id;
	    private String category;
	    private String title;
	    private String content;
}
