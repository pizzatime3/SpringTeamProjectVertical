package vertical.dto;


import vertical.domain.bimage.BimageEntity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BimageDto {

    private int b_ino;
    private String b_img;

    public BimageEntity toentity(){
        return BimageEntity.builder()
                .b_ino(this.b_ino)
                .b_img(this.b_img)
                .build();
    }


}
