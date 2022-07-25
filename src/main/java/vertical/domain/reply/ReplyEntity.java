package vertical.domain.reply;

import lombok.*;
import vertical.domain.BaseTime;
import vertical.domain.board.BoardEntity;
import vertical.domain.bimage.BimageEntity;
import vertical.domain.rimage.RimageEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder @Entity
@Table(name = "reply")
public class ReplyEntity extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rno;
    @Column(length = 1000 , nullable = false)
    private String rcontent;
    private int rlikes;
    private int rindex;  // 이게 상위 댓글 번호인거죠??이게 자동으로 0번이 들어가는데 대댓글을 쓰면 댓글 번호를 넣어주려고 만들었던거같아요..!

    @ManyToOne
    @JoinColumn(name = "bno")
    private BoardEntity boardEntity;

    @Builder.Default
    @OneToMany(mappedBy = "replyEntity" , cascade = CascadeType.ALL)
    List<RimageEntity> rimageEntities = new ArrayList<>();


}
