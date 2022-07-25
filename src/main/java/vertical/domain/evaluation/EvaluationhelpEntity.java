package vertical.domain.evaluation;

import lombok.*;
import vertical.domain.member.MemberEntity;

import javax.persistence.*;

@Entity @Table(name = "evaluation_help")
@AllArgsConstructor@NoArgsConstructor
@Getter@Setter@ToString@Builder
public class EvaluationhelpEntity {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ehno;
    int eno;
    String email;
}
