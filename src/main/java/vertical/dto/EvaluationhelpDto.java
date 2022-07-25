package vertical.dto;

import lombok.*;
import vertical.domain.evaluation.EvaluationEntity;
import vertical.domain.evaluation.EvaluationhelpEntity;

@Getter@Setter@ToString
@NoArgsConstructor@AllArgsConstructor
@Builder
public class EvaluationhelpDto {
    private int eno;
    private String email;

    public EvaluationhelpEntity toEntity(){
        return EvaluationhelpEntity.builder()
                .eno(this.eno)
                .email(this.email)
                .build();

    }


}
