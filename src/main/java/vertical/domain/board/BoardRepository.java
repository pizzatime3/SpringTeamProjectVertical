package vertical.domain.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vertical.domain.member.MemberEntity;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity , Integer> {

    @Query( value = "select * from board where cno = :cno and btitle like %:keyword%" , nativeQuery = true )
    Page<BoardEntity> findBybtitle(@Param("cno")int cno , @Param("keyword")String keyword , Pageable pageable);
    // List 대신 Page 사용하는이유 : Page 관련된 메소드를 사용하기 위해

    // 2. 내용 검색
    @Query( value = "select * from board where cno = :cno and bcontent like %:keyword%" , nativeQuery = true )
    Page<BoardEntity> findBybcontent(@Param("cno")  int cno ,    @Param("keyword") String keyword , Pageable pageable  );
    // 3. 작성자 검색
    @Query( value = "select * from board where cno = :cno and mno = :#{#memberEntity.mno}", nativeQuery = true  )
    Page<BoardEntity> findBymno(@Param("cno")int cno , @Param("memberEntity") MemberEntity memberEntity , Pageable pageable );

    // 카테고리별 게시판 조회수 높은 기준
    @Query(value = "select *, rank() over(order by bview desc)as ranking from board where cno = :cno limit 5", nativeQuery = true)
    List<BoardEntity> findBybview(@Param("cno") int cno);

    // 토픽베스트
    @Query(value = "select *, rank() over(order by bview desc)as ranking from board limit 10", nativeQuery = true)
    List<BoardEntity> findBybview();

    @Query(value = "select * from board where btitle like %:corpNm% or bcontent like %:corpNm% order by bno desc limit 2" , nativeQuery = true)
    List<BoardEntity> findByviewboard(@Param("corpNm") String corpNm);

    @Query(value = "select * from board where btitle like %:corpNm% or bcontent like %:corpNm% order by bno desc" , nativeQuery = true)
    List<BoardEntity> findBycompanyboard(@Param("corpNm") String corpNm);

    // 메인페이지 검색내용
    @Query(value = "select * from board where btitle like %:value% or bcontent like %:value%", nativeQuery = true)
    List<BoardEntity> findBysearch(@Param("value") String value);


}
