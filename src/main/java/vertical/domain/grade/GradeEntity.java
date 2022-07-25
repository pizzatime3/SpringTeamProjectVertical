package vertical.domain.grade;

import lombok.*;
import vertical.domain.evaluation.EvaluationEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name="grade")
@AllArgsConstructor@NoArgsConstructor
@Getter@Setter@Builder
public class GradeEntity {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gno;
    private int gtotal;
    private int gcareer;
    private int gbalance;
    private int gsalary;
    private int gculture;
    private int gmanagement;

    @Builder.Default
    @OneToMany(mappedBy = "gradeEntity", cascade = CascadeType.ALL)
    List<EvaluationEntity> evaluationEntities = new ArrayList<>();
}
