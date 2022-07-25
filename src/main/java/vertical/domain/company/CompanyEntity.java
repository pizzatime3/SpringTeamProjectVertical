package vertical.domain.company;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "company")
@Builder @Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int comno;
    private String comname;
    private String comemail;
    private String comfield;
    private String comlogo;
}
