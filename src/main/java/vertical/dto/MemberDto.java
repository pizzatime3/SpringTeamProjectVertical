package vertical.dto;

import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import vertical.domain.member.MemberEntity;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class MemberDto {
    private int mno;
    private String mname; // 닉네임
    private String memail;
    private String mpassword;
    private String mcom;

    // dto -> entity 변환 메소드(전체)
//    public MemberEntity toentity(){
//        // 패스워드 암호화
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//        return MemberEntity.builder()
//                .mname(this.mname)
//                .memail(this.memail)
//                .mpassword(passwordEncoder.encode(this.mpassword) )
//                .mcom(this.mcom)
//                .mrole("member")
//                .build();
//    }


}
