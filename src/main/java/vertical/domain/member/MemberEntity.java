package vertical.domain.member;

import lombok.*;
import vertical.domain.BaseTime;
import vertical.domain.board.BoardEntity;
import vertical.domain.likes.LikesEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Builder
@Getter  @Setter
@AllArgsConstructor  @NoArgsConstructor
public class MemberEntity extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mno;
    private String mcom;
    private String memail;
    private String mname;
    private String mpassword;
    private String mrole;

    @Builder.Default
    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL) // 일대다(1:M)
    List<LikesEntity> LikesEntity = new ArrayList<>();


    @Builder.Default
    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL) // 일대다(1:M)
    List<BoardEntity> boardEntityList = new ArrayList<>();

}
