package vertical.domain.evaluation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvaluationRepository extends JpaRepository<EvaluationEntity, Integer> {

    @Query(value = "SELECT * FROM evaluation WHERE firm = :firm ORDER BY createdate DESC", nativeQuery = true)
    List<EvaluationEntity> findByFirm(@Param("firm") String firm);

    @Query(value = "SELECT * FROM evaluation WHERE eoccupational_group = :eoccupational_group AND firm =:firm",nativeQuery = true)
    List<EvaluationEntity> findByEoccupational_group(@Param("firm") String firm, @Param("eoccupational_group") String eoccupational_group);

    @Query(value = "SELECT * FROM evaluation WHERE ewriter LIKE %:ewriter% AND firm =:firm",nativeQuery = true)
    List<EvaluationEntity> findByEwriter(@Param("firm") String firm, @Param("ewriter") String ewriter);

    @Query(value = "SELECT * FROM evaluation WHERE eoccupational_group = :eoccupational_group AND ewriter LIKE %:ewriter% AND firm =:firm",nativeQuery = true)
    List<EvaluationEntity> findByEoccupational_group_AndEwriter(@Param("firm") String firm, @Param("eoccupational_group") String eoccupational_group, @Param("ewriter") String ewriter);


}
