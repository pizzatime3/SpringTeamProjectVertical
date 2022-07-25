package vertical.dto;

import vertical.domain.category.CategoryEntity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CategoryDto {

    private int cno;
    private String cname;

    public CategoryEntity toentity(){
        return CategoryEntity.builder()
                .cno(this.cno)
                .cname(this.cname)
                .build();
    }

}
