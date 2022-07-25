package vertical.domain.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Integer> {

    // 회사메일을 통한 엔티티 검색
    Optional<CompanyEntity> findBycomemail(String comemail);

    // 회사명을 통한 엔티티 검색
    Optional<CompanyEntity> findBycomname(String comname);
}
