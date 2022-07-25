package vertical.dto;

import lombok.*;
import vertical.domain.evaluation.EvaluationEntity;

import javax.persistence.Table;

@AllArgsConstructor@NoArgsConstructor
@Getter@Setter@Builder@ToString
public class EvaluationDto {

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

    public EvaluationEntity toEntity(){
        return EvaluationEntity.builder()
                .eno(this.eno)
                .eoneline(this.eoneline)
                .epros(this.epros)
                .econs(this.econs)
                .echange(this.echange)
                .ewriter(this.ewriter)
                .estartdate(this.estartdate)
                .eenddate(this.eenddate)
                .eoccupational_group(this.eoccupational_group)
                .eregion(this.eregion)
                .firm(this.firm)
                .build();
    }






}
