package vertical.domain.bimage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface BimageRepository extends JpaRepository<BimageEntity, Integer> {

    @Query(value = "select * from bimage where bno = :bno", nativeQuery = true)
    List<BimageEntity> findBybino(@Param("bno") int bno);

}
