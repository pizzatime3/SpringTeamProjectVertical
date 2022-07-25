package vertical.domain.board;

import vertical.domain.BaseTime;
import vertical.domain.category.CategoryEntity;
import lombok.*;
import vertical.domain.bimage.BimageEntity;
import vertical.domain.likes.LikesEntity;
import vertical.domain.member.MemberEntity;
import vertical.domain.reply.ReplyEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor @NoArgsConstructor @Entity
@Getter @Setter @Builder @Table(name = "board")
public class BoardEntity extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bno;
    @Column(length = 500 , nullable = false)
    private String btitle;
    @Column(length = 1000 , nullable = false)
    private String bcontent;
    private int bview;
    private int blike;

    @Builder.Default
    @OneToMany(mappedBy = "boardEntity" , cascade = CascadeType.ALL)
    List<BimageEntity> bimageEntityList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "boardEntity" , cascade = CascadeType.ALL)
    List<ReplyEntity> replyEntities = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "boardEntity" , cascade = CascadeType.ALL)
    List<LikesEntity> likesEntities = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "cno")
    private CategoryEntity categoryEntity;

    // 작성자 추가(7/10 최으뜸)
    @ManyToOne
    @JoinColumn(name = "mno")
    private MemberEntity memberEntity;

}
