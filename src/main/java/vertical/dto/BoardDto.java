package vertical.dto;

import vertical.domain.board.BoardEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BoardDto {

    private int bno;
    private String btitle;
    private String bcontent;
    private int bview;
    private int blike;

    private int cno;


    private List<MultipartFile> b_img;

    public BoardEntity toentity(){
        return BoardEntity.builder()
                .bno(this.bno)
                .btitle(this.btitle)
                .bcontent(this.bcontent)
                .bview(this.bview)
                .blike(this.blike)
                .build();
    }

}
