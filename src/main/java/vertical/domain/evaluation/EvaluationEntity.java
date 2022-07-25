package vertical.domain.evaluation;

import lombok.*;
import vertical.domain.BaseTime;
import vertical.domain.grade.GradeEntity;

import javax.persistence.*;

@Entity @Table(name = "evaluation")
@AllArgsConstructor@NoArgsConstructor
@Getter@Setter@ToString@Builder
public class EvaluationEntity extends BaseTime {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eno;
    private String eoneline;
    private String epros;
    private String econs;
    private String echange;
    private String ewriter;
    private String estartdate;
    private String eenddate;
    private String eoccupational_group;
    private String eregion;
    private String firm;

    @ManyToOne
    @JoinColumn(name="gno")
    private GradeEntity gradeEntity;

}
