package vertical.domain.category;

import vertical.domain.board.BoardEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor  @NoArgsConstructor
@Getter  @Setter    @Entity
@Builder @Table(name = "category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cno;
    @Column(unique = true) //같은 이름 카테고리 생성x
    private String cname;

    @Builder.Default
    @OneToMany(mappedBy = "categoryEntity" , cascade = CascadeType.ALL)
    List<BoardEntity> boardEntityList = new ArrayList<>();

}
