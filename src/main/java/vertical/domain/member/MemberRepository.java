package vertical.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {

    // 이메일을 이용한 엔티티 검색
    Optional<MemberEntity> findBymemail(String memail);

    // 닉네임을 이용한 엔티티 검색
    Optional<MemberEntity> findBymname(String mname);
}
