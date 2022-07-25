package vertical.domain.rimage;

import lombok.*;
import vertical.domain.reply.ReplyEntity;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter  @Builder @Entity(name = "rimage")
public class RimageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int r_ino;

    private String r_img;

    @ManyToOne
    @JoinColumn(name = "rno")
    private ReplyEntity replyEntity;



}
