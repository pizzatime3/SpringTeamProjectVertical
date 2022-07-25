package vertical.domain.image;

import vertical.domain.board.BoardEntity;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Builder @Table(name = "image")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ino;
    @Column(length = 500)
    private String ifilename;
    private String ifilepath;

    @ManyToOne
    @JoinColumn(name = "bno")
    private BoardEntity boardEntity;


}
