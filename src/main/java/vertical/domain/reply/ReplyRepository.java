package vertical.domain.reply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vertical.domain.rimage.RimageEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReplyRepository extends JpaRepository<ReplyEntity, Integer> {

    @Query(value = "select * from reply where bno = :bno", nativeQuery = true)
    List<ReplyEntity> findBybno(int bno);

    @Query(value = "SELECT COUNT(*) FROM reply WHERE bno = :bno", nativeQuery = true)
    int replycount(@Param("bno") int bno);



}
