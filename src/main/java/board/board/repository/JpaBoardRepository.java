package board.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import board.board.entity.BoardEntity;
import board.board.entity.BoardFileEntity;

//JPA Repository로 사용하기 위해 CrudRepository 인터페이스 상속
public interface JpaBoardRepository extends CrudRepository<BoardEntity, Integer>{
	
	//쿼리 메서드 형태
	//규칙에 맞도록 리포지터리에 메서드를 추가(게시글 번호로 order by 내림차순으로 정렬)하면
	//메서드 이름에 따라 쿼리가 생성되어 실행된다.
	List<BoardEntity> findAllByOrderByBoardIdxDesc();
	
	//@Query를 통해 JPQL 직접 작성하는 형태 (SQL도 사용가능)
	@Query("SELECT file "
		 + "FROM "
		 + "	BoardFileEntity file "
		 + "WHERE "
		 + "	board_idx = :boardIdx "
		 + "	AND idx = :idx"
		)
	BoardFileEntity findBoardFile(@Param("boardIdx") int boardIdx, @Param("idx") int idx);

}
