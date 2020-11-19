package board.board.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

@ApiModel (value="BoardDto :  게시글 내용", description = "게시글 내용")
@Data
public class BoardDto {
	 
	@ApiModelProperty(value="게시글 번호")
	private int boardIdx;
	
	private String title;
	private String contents;
	private int hitCnt;
	private String creatorId;
	private String createdDatetime;
	private String updaterId;
	private String updatedDatetime;
	private List<BoardFileDto> fileList;

}
