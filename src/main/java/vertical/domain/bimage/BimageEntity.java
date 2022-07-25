package vertical.domain.bimage;

import lombok.*;
import vertical.domain.board.BoardEntity;

import javax.persistence.*;

@AllArgsConstructor  @NoArgsConstructor
@Entity  @Getter  @Setter  @Builder
@Table(name = "bimage")
public class BimageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int b_ino;

    private String b_img;

    @ManyToOne
    @JoinColumn(name = "bno")
    private BoardEntity boardEntity;


}
