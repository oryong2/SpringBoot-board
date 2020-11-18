package board.board.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

//해당 클래스가 JPA 엔티티 (테이블과 매핑) 임을 나타낸다.
@Entity
//DB의 특정 테이블과 매핑 됨을 나타냄
@Table(name = "t_jpa_board")
@NoArgsConstructor
@Data
public class BoardEntity {
	
	//엔티티의 PK임을 나타냄
	@Id
	//기본 키(PK) 생성전략을 설정. GenerationType.AUTO : DB에서 제공하는 PK 생성 전략을 따름.
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int boardIdx;
	
	//컬럼에 NOT NULL 속성을 지정
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String contents;
	
	@Column(nullable = false)
	private int hitCnt = 0;
	
	@Column(nullable = false)
	private String creatorId;
	
	@Column(nullable = false)
	private LocalDateTime createdDatetime = LocalDateTime.now();
	
	
	private String updaterId;
	private String updatedDatetime;
	
	//1:N의 관계를 표현
	@OneToMany (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	//릴레이션 관계가 있는 테이블의 컬럼을 지정
	@JoinColumn(name = "board_idx")
	private Collection<BoardFileEntity> fileList;

}
