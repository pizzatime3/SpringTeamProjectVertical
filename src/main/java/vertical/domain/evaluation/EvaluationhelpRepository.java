package vertical.domain.evaluation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EvaluationhelpRepository extends JpaRepository<EvaluationhelpEntity, Integer> {
    @Query(value = "SELECT COUNT(*) FROM evaluation_help WHERE eno = :eno", nativeQuery = true)
    int gethelpcount(@Param("eno") int eno);

    @Query(value = "SELECT * FROM evaluation_help  WHERE eno = :eno AND email = :email",nativeQuery = true)
    Optional<EvaluationhelpEntity> check(@Param("eno") int eno, @Param("email") String email);

    @Query(value = "SELECT ehno FROM evaluation_help  WHERE eno = :eno AND email = :email",nativeQuery = true)
    EvaluationhelpEntity getehno(@Param("eno") int eno, @Param("email") String email);



}
