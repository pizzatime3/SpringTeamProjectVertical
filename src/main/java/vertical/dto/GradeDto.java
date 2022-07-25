package vertical.dto;

import lombok.*;
import vertical.domain.grade.GradeEntity;

@AllArgsConstructor@NoArgsConstructor
@Getter@Setter@Builder@ToString
public class GradeDto {

    private int gno;
    private int gtotal;
    private int gcareer;
    private int gbalance;
    private int gsalary;
    private int gculture;
    private int gmanagement;

    public GradeEntity toEntity(){
        return GradeEntity.builder()
                .gno(this.gno)
                .gtotal(this.gtotal)
                .gcareer(this.gcareer)
                .gbalance(this.gbalance)
                .gsalary(this.gsalary)
                .gculture(this.gculture)
                .gmanagement(this.gmanagement)
                .build();
    }
}
