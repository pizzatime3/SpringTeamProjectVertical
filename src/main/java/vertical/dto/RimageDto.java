package vertical.dto;

import lombok.*;
import vertical.domain.rimage.RimageEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class RimageDto {

    private int r_ino;
    private String r_img;

    public RimageEntity toentity(){
        return RimageEntity.builder()
                .r_ino(this.r_ino)
                .r_img(this.r_img)
                .build();
    }

}
