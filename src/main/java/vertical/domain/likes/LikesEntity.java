package vertical.domain.likes;

import lombok.*;
import vertical.domain.board.BoardEntity;
import vertical.domain.member.MemberEntity;

import javax.persistence.*;

@AllArgsConstructor  @NoArgsConstructor
@Entity  @Getter  @Setter
@Builder @Table(name = "likes")
public class LikesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lno;

    private int likes; //좋아요 수
    //cascade 옵션으로 멤버, 게시판 삭제 시 좋아요도 삭제 되도록 (안됨..)

    @ManyToOne
    @JoinColumn(name = "mno")
    private MemberEntity memberEntity;

    @ManyToOne
    @JoinColumn(name = "bno")
    private BoardEntity boardEntity;

}
