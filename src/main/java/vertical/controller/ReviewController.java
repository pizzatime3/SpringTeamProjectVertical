package vertical.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vertical.dto.EvaluationhelpDto;
import vertical.service.EvaluationService;
import vertical.service.MemberService;
import vertical.service.ReviewService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller

public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    MemberService memberService;

    @Autowired
    EvaluationService evaluationService;

    //////////////////////////////////////// 1. view 열기[ 템플릿 연결 ] 매핑 //////////////////

    @GetMapping("/review")
    private String list(){
        return "/review/list";
    }

    @GetMapping("/review/company/{name}")
    public String company(@PathVariable("name") String name, Model model){
        request.getSession().setAttribute("name", name);
        model.addAttribute("data", name);
        //model.addAttribute("homepage", homepage);
        return "review/view";
    }

    @GetMapping("/review/write")
    public String write(Model model){
        String name = (String) request.getSession().getAttribute("name");
        model.addAttribute("data", name);
        return "review/write";
    }
    @GetMapping("/review/mycomwrite")
    public String mycomwrite(@ModelAttribute(name = "email") String email, Model model){
        if(email.equals("anonymousUser")){
            return "member/login";
        }
        String com = (String) memberService.getinfo(email).get("mcom");
        request.getSession().setAttribute("name", com);
        return "redirect:/review/write";
    }

    @GetMapping("/review/onhelp")
    @ResponseBody
    public boolean onhelp(EvaluationhelpDto evaluationhelpDto){
//        if(email.equals("anonymousUser")){
//            return false;
//        }
//        boolean result = evaluationService.onhelp(email, eno);
        if (evaluationhelpDto.getEmail().equals("anonymousUser")){
            return false;
        } else {
            boolean result = evaluationService.onhelp(evaluationhelpDto);
            return result;
        }
    }

    @GetMapping("/review/companyreview/{name}")
    public String companyreview(@PathVariable("name") String name, Model model){
        request.getSession().setAttribute("name", name);
        model.addAttribute("data", name);
        //model.addAttribute("homepage", homepage);
        return "review/companyreview";
    }

    @GetMapping("/review/companyboard/{name}")
    public String companyboard(@PathVariable("name") String name, Model model){
        request.getSession().setAttribute("name", name);
        model.addAttribute("data", name);
        //model.addAttribute("homepage", homepage);
        return "review/companyboard";
    }

    @GetMapping("/review/companyrecruit/{name}")
    public String companyrecruit(@PathVariable("name") String name, Model model){
        request.getSession().setAttribute("name", name);
        model.addAttribute("data", name);
        //model.addAttribute("homepage", homepage);
        return "review/companyrecruit";
    }


    /////////////////////////////////////// 2. service 처리 매핑 ///////////////////////////////////////


//    @GetMapping("/review/getlist")
//    @ResponseBody
//    private void getlist(HttpServletResponse response) {
//        try {
//            response.setContentType("application/json");
//            response.setCharacterEncoding("UTF-8");
//            response.getWriter().println(reviewService.getlist());
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }

    //////////////////인기기업출력//////////////
    @GetMapping("/review/getpopularlist")
    @ResponseBody
    private void getpopularlist(HttpServletResponse response) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(reviewService.getpopularlist());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    //////////////////인기기업출력 END//////////////


    ////////////////////////기업이름클릭시 뿌려줄정보//////////////
    @GetMapping("/review/getcorp")
    @ResponseBody
    private void getcorp(HttpServletResponse response, @RequestParam("corpNm") String corpNm) {

        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(reviewService.getcorp(corpNm));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    ////////////////////////기업이름클릭시 뿌려줄정보  END//////////////


    /////////////////////======기업검색출력=====//////////////
    @GetMapping("/review/search")
    @ResponseBody
    private void search(HttpServletResponse response, @RequestParam("corpNm") String corpNm) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(reviewService.search(corpNm));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    /////////////////////======기업검색출력  END=====//////////////

    /////////////////////======채용공고 크롤링======////////////////
//    @GetMapping("/review/recruit")
//    @ResponseBody
//    private void getrecruit(HttpServletResponse response, @RequestParam("corpNm") String corpNm){
//        try {
//            response.setContentType("application/json");
//            response.setCharacterEncoding("UTF-8");
//            response.getWriter().println(reviewService.recruit(corpNm));
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
    /////////////////////======채용공고 크롤링 END======////////////////


}