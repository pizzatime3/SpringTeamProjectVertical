package vertical.domain.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vertical.domain.board.BoardEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<LikesEntity, Integer> {

    @Query(value = "select * from likes where bno = :bno and mno = :mno", nativeQuery = true)
    Optional<LikesEntity> liekscheack(@Param("bno") int bno , @Param("mno") int mno );

    @Query(value = "SELECT COUNT(*) FROM likes WHERE bno = :bno", nativeQuery = true)
    int likecount(@Param("bno") int bno);

    @Query(value = "select *, rank() over(order by lno desc)as ranking from likes where bno = :bno limit 5", nativeQuery = true)
    int findByblike(@Param("bno") int bno);

}
