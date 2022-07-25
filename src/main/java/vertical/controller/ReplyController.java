package vertical.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vertical.dto.ReplyDto;
import vertical.service.ReplyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ReplyService replyService;;

    ///////////////////////////////////////////////// 댓글 구역 ///////////////////////////////////////////////////

    @PostMapping("/save")
    @ResponseBody
    public boolean save(ReplyDto replyDto){
        int bno = (Integer) request.getSession().getAttribute("bno");
        replyDto.setBno(bno);
        return replyService.save(replyDto);
    }

    @GetMapping("/getreplylist")
    public void getreplylist(HttpServletResponse response){
        int bno = (Integer) request.getSession().getAttribute("bno");
        System.out.println("게시물번호 : " + bno);
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(replyService.getreplylist(bno));
        }catch (Exception e){ System.out.println(e); }
    }

    @GetMapping("/update/{rno}")
    public String update(@PathVariable("rno") int rno, Model model){
        model.addAttribute("rno", rno);
        request.getSession().setAttribute("rno", rno);
        return "reply/update";
    }

    @PutMapping("/update")
    @ResponseBody
    public boolean update(ReplyDto replyDto){
        int bno = (Integer) request.getSession().getAttribute("bno");
        return replyService.update(replyDto);

    }
    @GetMapping("/getreply")
    public void getreply(int rno, HttpServletResponse response){
        try{
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(replyService.getreply(rno));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public int delete(@RequestParam("rno") String rno){
        replyService.delete(Integer.parseInt(rno));
        int bno = (Integer) request.getSession().getAttribute("bno");
        return bno;
    }

    @DeleteMapping("/rimgdelete")
    @ResponseBody
    public boolean rimgdelete(@RequestParam("r_ino") int r_ino){
        return replyService.rimgdelete(r_ino);
    }

    //////////////////////////////////////////////////////////// 대댓글 구역 //////////////////////////////////////////////////////////

    @PostMapping("/reresave")
    @ResponseBody
    public boolean reresave( @RequestParam("rno")int rno ,
             @RequestParam("rereplycontent") String rereplycontent){

        return replyService.reresave( rno , rereplycontent );
    }

}
