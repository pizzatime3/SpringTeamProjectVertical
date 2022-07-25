package vertical.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vertical.dto.EvaluationDto;
import vertical.dto.GradeDto;
import vertical.service.EvaluationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/evaluation")
public class EvaluationController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private EvaluationService evaluationService;
    @PostMapping("/write")
    @ResponseBody
    public boolean write(EvaluationDto evaluationDto, GradeDto gradeDto){
        System.out.println(evaluationDto.toString());
        System.out.println(gradeDto.toString());
        return evaluationService.write(evaluationDto, gradeDto);
    }

    @GetMapping("/list")
    @ResponseBody
    public void getlist(@RequestParam("firm") String firm, @RequestParam("title") String title, @RequestParam("status") String status, @RequestParam("option") String option, HttpServletResponse response){
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(evaluationService.getlist(firm, title, status, option));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @GetMapping("/detaillist")
    @ResponseBody
    public void detailist(@RequestParam("eno") int eno, HttpServletResponse response){
        try{
            response.setCharacterEncoding("UTF-8");
            response.setContentType("applicaion/json");
            response.getWriter().print(evaluationService.getdetailstar(eno));
        } catch (Exception e){
            e.printStackTrace();
        }
    }



}
