package vertical.dto;

import vertical.domain.likes.LikesEntity;
import lombok.*;

@AllArgsConstructor  @NoArgsConstructor
@Getter  @Setter  @ToString  @Builder
public class LikesDto {

    private int lno;
    private int likes;

}
