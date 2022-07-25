package vertical.dto;

import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class CompanyDto {
    private int comno;
    private String comname;
    private String comemail;
    private String comfield;
}
