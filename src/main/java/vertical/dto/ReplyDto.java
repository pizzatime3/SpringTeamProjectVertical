package vertical.dto;

import vertical.domain.reply.ReplyEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ReplyDto {

    private int rno;
    private String rcontent;
    private int rlikes;
    private int rindex;

    private int bno;

    private List<MultipartFile> r_img;

    public ReplyEntity toentity(){
        return ReplyEntity.builder()
                .rno(this.rno)
                .rcontent(this.rcontent)
                .rlikes(this.rlikes)
                .rindex(this.rindex)
                .build();
    }

}
