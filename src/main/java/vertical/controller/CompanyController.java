package vertical.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import vertical.service.CompanyService;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    ///////////////////////////// 기능 처리 매핑 //////////////////////////////
    @GetMapping("/companylist")
    @ResponseBody
    public void getcompanylist(HttpServletResponse response){
        try{
           response.setCharacterEncoding("UTF-8");
           response.setContentType("application/json");
           response.getWriter().print(companyService.getcompanylist() );
        }catch(Exception e){System.out.println("메인페이지회사목록호출오류"+e);}
    }

    @GetMapping("/getcompany")
    @ResponseBody
    public void getcompany(HttpServletResponse response, @RequestParam("comname") String comname){
        try{
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(companyService.getcompany(comname) );
        }catch(Exception e){System.out.println("메인페이지회사목록호출오류"+e);}
    }
}
