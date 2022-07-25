package vertical.domain.rimage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RimageRepository extends JpaRepository<RimageEntity, Integer> {
    @Query(value = "select * from rimage where rno = :rno", nativeQuery = true)
    List<RimageEntity> replydelete(@Param("rno") int rno);
}
