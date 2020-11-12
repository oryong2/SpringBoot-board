package board.board.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class BoardDto {
	 
	private int boardIdx;
	private String title;
	private String contents;
	private int hitCnt;
	private String creatorId;
	private String createdDatetime;
	private String updaterId;
	private String updatedDatetime;

}
