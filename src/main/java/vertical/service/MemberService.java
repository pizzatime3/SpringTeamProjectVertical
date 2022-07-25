package vertical.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vertical.domain.board.BoardEntity;
import vertical.domain.board.BoardRepository;
import vertical.domain.category.CategoryRepository;
import vertical.domain.company.CompanyEntity;
import vertical.domain.company.CompanyRepository;
import vertical.domain.member.MemberEntity;
import vertical.domain.member.MemberRepository;
import vertical.dto.LoginDto;

// 자바 메일 전송 인터페이스 import

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class MemberService implements UserDetailsService {

    // memberRepository 호출
    @Autowired
    private MemberRepository memberRepository;

    // companyRepository 호출(회원가입 시 회사명 가져오기)
    @Autowired
    private CompanyRepository companyRepository;

    // boardRepository 호출(내가 작성한 글)
    @Autowired
    private BoardRepository boardRepository;

    // categoryRepository 호출(내가 작성한 글 호출시 카테고리 이름)
    @Autowired
    private CategoryRepository categoryRepository;

    // 세션 호출
    @Autowired
    private HttpServletRequest request;


    // 1. 회원가입 처리 메소드
    @Transactional
    public boolean signup(String memail, String mpassword){
        // 회사명 가져오기
        String emailaddress = memail.split("@")[1];
        String companyname = findcomname(emailaddress);
        // 닉네임 -> 랜덤문자
        String nickname = makenickname();
        // 패스워드 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 이메일 DB에서 찾기
        Optional<MemberEntity> optional = memberRepository.findBymemail(memail);
        MemberEntity memberEntity = optional.get();
        // 해당이메일 레코드에 회사, 비밀번호, 닉네임, 권한 등록
        memberEntity.setMpassword(passwordEncoder.encode(mpassword));
        memberEntity.setMname(nickname);
        memberEntity.setMrole("member");
        memberEntity.setMcom(companyname); ///////////////////// 회사명 변경하기 ////////////
        return true;

    } // 회원가입 처리 end

    // 1-2. 회원가입시 이메일 중복체크 메소드
    public int emailcheck(String email){
        Optional<MemberEntity> optional = memberRepository.findBymemail(email);
        MemberEntity memberEntity = null;

        if(optional.isPresent()){ // 이메일이 존재하면
            return 1;
        }else{ // 이메일 중복이 없으면
            String emailaddress = email.split("@")[1];
            // 회사가 존재하는지 확인
            String companyname = findcomname(emailaddress);
            if(companyname == null){ // 회사명이 없으면
                return 2;
            }else {
                // 이메일에 들어가는 내용 [html]
                StringBuilder html = new StringBuilder();
                html.append("<html> <body> <h1> Blind </h1><br> ");

                //인증코드[난수] 만들기
                Random random = new Random();
                StringBuilder verificationcode = new StringBuilder(); // 인증코드
                // 문자랜덤 만들기
                for (int i = 0; i < 5; i++) {
                    char ranmsg = (char) (random.nextInt(26) + 97);  // 97~122 : 소문자 a->z 중 하나 난수 발생
                    System.out.println("문자난수 : " + ranmsg); ////////////////////////////// 테스트(지우기)
                    verificationcode.append(ranmsg);
                } // for end
                // 숫자랜덤 만들기
                char rannum = (char) (random.nextInt(10) + 48);
                System.out.println("숫자난수 : " + rannum);  ////////////////////////////// 테스트(지우기)
                // 최종 인증코드
                verificationcode.insert(4, rannum);
                System.out.println("최종인증코드 : " + verificationcode);  ////////////////////////////// 테스트

                // 인증코드 전달
                html.append("<p>익명 커뮤니티를 이용을 위해 아래 코드를 홈페이지 화면에 입력해주세요.</p><br>" +
                        "<p> 인증코드 : " + verificationcode + "</p><br>" +
                        "<p>인증을 위해 사용된 이메일 정보와 서비스를 이용하기 위한 정보는 매칭되지 않으며 어떤 이메일 소유자가 글을 썼는지에 대한 정보는 본인 이외에 알 수 없으니 안심하고 이용하세요.</p>");
                html.append("</body> </html>");

                // memberRepository에 이메일과 인증코드를 mcom 임시저장
                memberEntity = MemberEntity.builder()
                        .memail(email)
                        .mcom(verificationcode.toString())
                        .build();
                memberRepository.save(memberEntity);

                // 회원가입 인증 메일 보내기
                mailsend(email, "[Blind]게시판 이용을 위해 이메일 인증을 완료해주세요.", html);
                return 3;
            } // else end
        } // else end
    }

        // 자바 메일 전송 인터페이스 빈 생성
        @Autowired
        private JavaMailSender javaMailSender;

    // 1-3. 회원가입시 메일전송 메소드
    public void mailsend(String getemail, String title, StringBuilder content){
        try {
            // 이메일 전송
            MimeMessage message = javaMailSender.createMimeMessage();
            // 1. Mime 설정 (Mime 프로토콜 : 메시지안에 텍스트외 내용을 담는 프로토콜)
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, "UTF-8");  // 예외처리 발생
            // 2. 보내는 사람
            mimeMessageHelper.setFrom("@naver.com", "Blind 이메일인증");

            // 3. 받는 사람
            mimeMessageHelper.setTo(getemail);
            // 4. 메일 제목
            mimeMessageHelper.setSubject(title);
            // 5. 메일 내용
            mimeMessageHelper.setText(content.toString(), true);
            // 6. 메일 전송
            javaMailSender.send(message);
        }catch(Exception e) {
            e.printStackTrace();
            // 메일전송 실패시 db에 저장된 이메일, 인증번호 지우기
                // 1. 이메일 찾기
                Optional<MemberEntity> optional = memberRepository.findBymemail(getemail);
                MemberEntity memberEntity = optional.get();
                // 2. 이메일, 인증번호 지우기
                memberRepository.delete(memberEntity);
        } // catch end
    } // 회원가입시 메일전송 end

    // 1-4. 회원가입시 인증번호 확인 메소드
    public boolean randomcheck(String randomnum, String email){
        Optional<MemberEntity> optional = memberRepository.findBymemail(email);
        if(optional.isPresent() ){ // optional이 null이 아니면
            MemberEntity memberEntity = optional.get(); // 회원이메일로 찾은 엔티티 가져오기
            if(randomnum.equals(memberEntity.getMcom()) ){ // 만약에 입력한 인증번호와 엔티티에 임시저장된 인증번호가 동일하면
                return true; // 성공
            } // if end
        } // if end
        return false; // 실패
    } // 회원가입시 인증번호 확인 end

    // 1-5. 닉네임 생성 메소드
    public String makenickname(){
        Random random = new Random();
        StringBuilder nickname = new StringBuilder();
        for(int i = 0; i < 6; i++){
            int choice = random.nextInt(3);
            switch(choice){
                case 0: // 대문자 랜덤
                    nickname.append( (char)(random.nextInt(26)+65) );
                    break;
                case 1: // 소문자 랜덤
                    nickname.append( (char)(random.nextInt(26)+97) );
                    break;
                case 2: // 숫자 랜덤
                    nickname.append( (char)(random.nextInt(10)+48) );
                    break;
                default:
                    break;
            } // switch end
        } // for end
        System.out.println("닉네임 랜덤 : "+nickname);
        return nickname.toString();
    } // 닉네임 랜덤문자 생성 end

    // 1-6. 회사명 찾기
    public String findcomname(String memailaddress){
        Optional<CompanyEntity> optional = companyRepository.findBycomemail(memailaddress);
        if(optional.isPresent() ){ // 메일이 존재하면
            return optional.get().getComname();
        } // if end
        return null; // 회사 db에 없으면
    } // 회사명 찾기 end


    // 2. 로그인 메소드(아이디 검증)
    @Override
    public UserDetails loadUserByUsername(String memail) throws UsernameNotFoundException {
        // DB에 해당하는 이메일 있는지 찾기
        Optional<MemberEntity> optional = memberRepository.findBymemail(memail);
        MemberEntity memberEntity = optional.get();
        if (optional.isPresent()){ // optional이 null이 아니면

            List<GrantedAuthority> authorities = new ArrayList<>();
                // List<GrantedAuthority> : 부여된 인증들을 모아두기
                    // GrantedAuthority : 부여된 인증의 클래스
            authorities.add(new SimpleGrantedAuthority(memberEntity.getMrole()));
            return LoginDto.builder()
                    .memail(memberEntity.getMemail())
                    .mpassword(memberEntity.getMpassword())
                    .authorities(Collections.singleton(new SimpleGrantedAuthority(memberEntity.getMrole())))
                    .build();
        }
        return null;
    } // 로그인처리 end

    // 3. 내정보 불러오기
    public JSONObject getinfo(String memail){
        // 이메일로 DB에서 찾기
        Optional<MemberEntity> optional = memberRepository.findBymemail(memail);
        if(!optional.isPresent() ){return null;}
        MemberEntity memberEntity = optional.get();

        JSONObject object = new JSONObject();
        object.put("mname", memberEntity.getMname() );
        object.put("mcom", memberEntity.getMcom() );
        return object;
    } // 내정보호출 end


    // 4. 회원정보 수정 메소드
    @Transactional
    public boolean update(String mname){
        // 세션?에 저장된 이메일 가져오기
        String memail = SecurityContextHolder.getContext().getAuthentication().getName();
        if(memail == null){ // 세션이 없으면
            return false;
        }
        // DB에서 해당이메일 엔티티 가져오기
        Optional<MemberEntity> optional = memberRepository.findBymemail(memail);
        MemberEntity memberEntity = optional.get();
        // 수정하기
        memberEntity.setMname(mname);
        // 반환
        return true;
    } // 회원정보 수정 end

    // 4-2. 회원정보 수정 시 닉네임 중복체크
    public boolean mnamecheck(String mname){
        // 닉네임으로 엔티티검색
        Optional<MemberEntity> optional = memberRepository.findBymname(mname);
        if(optional.isPresent()) { // 닉네임이 존재하면
            return false;
        }else{ // 닉네임 중복 X
            return true;
        } // else end
    } // 닉네임중복체크 end

    // 5. 비밀번호 변경 메소드
    @Transactional
    public boolean passwordupdate(String newpassword){
        // 세션?에 저장된 이메일 가져오기
        String memail = SecurityContextHolder.getContext().getAuthentication().getName();
        // 이메일로 엔티티 검색
        Optional<MemberEntity> optional = memberRepository.findBymemail(memail);
        MemberEntity memberEntity = optional.get();
        // 패스워드 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberEntity.setMpassword(passwordEncoder.encode(newpassword));
        return true;
    } // 비밀번호 변경 end

    // 5-2. 비밀번호 일치여부 메소드
    public boolean passwordcheck(String mpassword){

        // 1. 인증된 세션 호출[시큐리티 인증 결과]
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginDto principal = (LoginDto)authentication.getPrincipal();
        String password = principal.getPassword();

        System.out.println("password : " +password ); ///////////////////////// 테스트

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        System.out.println("password2 : " + passwordEncoder.matches( mpassword , password ) ); ///////////////////////// 테스트

       // 입력된 비밀번호값과 db에 저장된 비밀번호값 일치여부(결과값 true/false)
        return passwordEncoder.matches(mpassword, password);
    } // 비밀번호 일치여부 end

    // 6. 회원탈퇴 메소드
    public boolean delete(){
        // 1. 인증된 세션 호출[시큐리티 인증 결과]
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 2. 이메일 정보 가져오기
        String memail = authentication.getName();
        // 3. DB에서 이메일 찾기
        Optional<MemberEntity> optional = memberRepository.findBymemail(memail);
        MemberEntity memberEntity = optional.get();
        // 4. DB에서 지우기
        memberRepository.delete(memberEntity);
        return true;
    } // 회원탈퇴 end

    // 7. 회원이메일로 회원번호 불러오기 메소드
    public int getmno(){
        // 1. 인증된 세션의 이메일 호출
        String memail = SecurityContextHolder.getContext().getAuthentication().getName();
        // 2. DB에서 이메일 찾기
        Optional<MemberEntity> optional = memberRepository.findBymemail(memail);
        if(optional.isPresent() ){
            return optional.get().getMno();
        }else{ // 해당 이메일이 db에 없으면
            return 0;
        } // else end

    } // 회원번호 불러오기 end

    // 8. 내가 작성한 글 가져오기 메소드
    public JSONArray getmyboard(int mno){
        // jsonarray 선언
        JSONArray jsonArray = new JSONArray();
        // 회원번호로 회원엔티티검색
        Optional<MemberEntity> optional = memberRepository.findById(mno);
        if(!optional.isPresent() ){return null;}

        MemberEntity memberEntity = optional.get();

        // 찾은 회원 엔티티의 게시물 목록 json형 변환
        for(BoardEntity entity : memberEntity.getBoardEntityList() ){
            JSONObject object = new JSONObject();
                object.put("btitle", entity.getBtitle() );
                object.put("cname", categoryRepository.findById(entity.getCategoryEntity().getCno() ).get().getCname() );
                object.put("bdate", entity.getCreatedate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")));
                jsonArray.put(object);
        } // for end
        return jsonArray;
    } // 내가 작성한 글 가져오기 end

}
