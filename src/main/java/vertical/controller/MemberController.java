package vertical.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vertical.service.MemberService;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/member")
public class MemberController {

    // memberService 호출
    @Autowired
    private MemberService memberService;


    ///////////////////////////// 페이지 이동 매핑 //////////////////////////////
    // 1. 회원가입 페이지 이동
    @GetMapping("/signup")
    public String signup(){return "member/signup";}

    // 2. 로그인 페이지 이동
    @GetMapping("/login")
    public String login(){return "member/login";}

    // 3. 회원정보 페이지 이동
    @GetMapping("/info")
    public String info(){return "member/info";}

    // 4. 회원정보수정 페이지 이동
    @GetMapping("/update")
    public String update(){return "member/update";}

    // 5. 비밀번호변경 페이지 이동
    @GetMapping("/pwupdate")
    public String pwupdate(){return "member/pwupdate";}

    // 6. 내가쓴글 페이지 이동
    @GetMapping("/myboard")
    public String myboard(){return "member/myboard";}

//    // 5. 회원탈퇴 페이지 이동 (필요없을거 같음)
//    @GetMapping("/delete")
//    public String delete(){return "/member/delete";}

    ///////////////////////////// 기능 처리 매핑 //////////////////////////////

    // 1. 회원가입 처리
    @PostMapping("/signup")
    @ResponseBody
    public boolean member_save(@RequestParam("memail") String memail, @RequestParam("mpassword") String mpassword){
        System.out.println("zzzzzzzzzzzzzzzzzzzz");
        return memberService.signup(memail, mpassword);
    }

    // 1-2. 회원가입시 이메일 중복체크 처리
    @GetMapping("/emailcheck")
    @ResponseBody
    public int member_emailcheck(@RequestParam("email") String email){
        int result = memberService.emailcheck(email);
        return result;
    }

    // 1-3. 회원가입시 이메일 인증번호 확인 처리
    @GetMapping("/randomcheck")
    @ResponseBody
    public boolean member_randomcheck(@RequestParam("randomnum") String randomnum, @RequestParam("email") String email){
        return memberService.randomcheck(randomnum, email);

    }

    // 2. 내정보 불러오기 처리
    @GetMapping("/getinfo")
    @ResponseBody
    public void member_info(HttpServletResponse response){
        // 세션?에 저장된 이메일 가져오기
        String memail = SecurityContextHolder.getContext().getAuthentication().getName();
        try{
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(memberService.getinfo(memail) );
        }catch (Exception e){System.out.println("내정보불러오기 오류 : "+ e);}
    }

    // 3. 회원정보수정 처리
    @PutMapping("/update")
    @ResponseBody
    public boolean member_update(@RequestParam("mname") String mname){
        return memberService.update(mname);
    }

    // 3-2. 회원정보수정시 닉네임 중복체크
    @GetMapping("/mnamecheck")
    @ResponseBody
    public boolean member_mnamecheck(@RequestParam("mname") String mname){
        return memberService.mnamecheck(mname);
    } // 닉네임 중복체크 end

    // 3-3. 현재비밀번호 DB비밀번호 일치확인
    @PostMapping("/passwordcheck")
    @ResponseBody
    public boolean member_passwordcheck(@RequestParam("mpassword") String mpassword){
        return memberService.passwordcheck(mpassword);
    }

    // 3-4. 비밀번호 변경
    @PostMapping("/pwupdate")
    @ResponseBody
    public boolean member_pwupdate(@RequestParam("newpassword") String newpassword){
        return  memberService.passwordupdate(newpassword);
    }

    // 4. 회원탈퇴 처리
    @DeleteMapping("/delete")
    @ResponseBody
    public boolean member_delete(){return memberService.delete();}

    // 5. 내가쓴글 처리
    @GetMapping("/getmyboard")
    public void getmyboard(HttpServletResponse response){
        // 회원번호 가져오기
        int mno = memberService.getmno();
        try{
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(memberService.getmyboard(mno) );
        }catch (Exception e){e.printStackTrace();}

    }

}
