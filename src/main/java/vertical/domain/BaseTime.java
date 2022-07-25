package vertical.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseTime {

    @CreatedDate // 생성 날자/시간 주입
    @Column(updatable = false) // 최초 저장 후 수정 금지
    private LocalDateTime createdate;

    @LastModifiedDate // 수정 날짜/시간 주입
    private LocalDateTime modifiedate;

}
