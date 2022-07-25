package vertical;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class VerticalApplication {

    public static void main(String[] args) {
        SpringApplication.run(VerticalApplication.class, args);
    }

}

//crno	법인등록번호
//enpBsadr	기업기본주소
//enpHmpgUrl	기업홈페이지URL
//sicNm	표준산업분류명
//enpEstbDt	기업설립일자
//enpEmpeCnt	기업종업원수
//enpPn1AvgSlryAmt	기업의1인평균급여금액