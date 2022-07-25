package vertical.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import vertical.domain.board.BoardEntity;
import vertical.domain.board.BoardRepository;
import vertical.domain.category.CategoryEntity;
import vertical.domain.category.CategoryRepository;
import vertical.dto.BoardDto;
import vertical.service.BoardService;
import vertical.service.LikesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import vertical.dto.BoardDto;
import vertical.service.BoardService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private BoardRepository boardRepository;

    // 게시판 페이지 열기
    @GetMapping("/save")
    public String save(){
        return "board/save";
    }

    // 게시판 리스트
    @GetMapping("/list")
    public String list(){
        return "board/list";
    }

    int selectbno = 0;

    // 게시판 개별 조회 페이지
    @GetMapping("/view/{bno}")
    public String view(@PathVariable("bno") int bno){
        request.getSession().setAttribute("bno" , bno);
        selectbno = bno;
        return "board/view";
    }

    // 페이지 열기
    @GetMapping("/update/{bno}")
    public String update(@PathVariable("bno") int bno ) {
        request.getSession().setAttribute("bno" , bno);
        selectbno = bno;
        return "board/update";
    }




    ///////////////////////////////////////게시물 구역//////////////////////////////////////////////

    // 게시물 저장 메소드
    @PostMapping("/save")
    @ResponseBody
    public boolean save(BoardDto boardDto) {
        return boardService.save(boardDto);
    }


    // 모든 게시물 출력 메소드
    @GetMapping("/getboardlist")
    @ResponseBody
    public void getboardlist(HttpServletResponse response,
            @RequestParam("cno") int cno ,
            @RequestParam("key") String key ,
            @RequestParam("keyword") String keyword,
             @RequestParam("page") int page ,
             @RequestParam("orderval") int orderval ){

        // 메인페이지에서 더보기로 바로 카테고리별 게시판 이동
        if (request.getSession().getAttribute("cno") != null) cno= (int) request.getSession().getAttribute("cno");


        try{
            request.getSession().setAttribute("cno", null);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().println(boardService.getboardlist(cno, key, keyword, page , orderval ));
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    // 개별 조회 메소드
    @GetMapping("/getboard")
    public void getboard(HttpServletResponse response, Model model){
        int bno = (Integer)request.getSession().getAttribute("bno");
        model.addAttribute("board" , boardService.getboard(bno));

        try{
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(boardService.getboard(bno));

        }catch (Exception e){e.printStackTrace();}
    }


    // 게시물 수정 메소드
    @PutMapping("/update")
    @ResponseBody
    public boolean update(BoardDto boardDto) {
        int bno = (Integer) request.getSession().getAttribute("bno");
        boardDto.setBno(bno);
        return boardService.update(boardDto);
    }

    // 게시물 삭제 메소드
    @DeleteMapping("/delete")
    @ResponseBody
    public boolean delete(@RequestParam("bno") int bno){
        return boardService.delete(bno);
    }

    @DeleteMapping("/bimgdelete")
    @ResponseBody
    public boolean bimgdelete(@RequestParam("b_ino") int b_ino){
        System.out.println(b_ino);
        return boardService.bimgdelete(b_ino);
    }

//////////////////////////////////게시물 좋아요///////////////////////////////////

    @Autowired
    public LikesService likesService;

    //좋아요 만들기
    @PostMapping("likesave")
    @ResponseBody
    public boolean likesave(@RequestParam("bno") int bno){
        return boardService.likesave(bno);
    }

    //카테고리 호출
    @GetMapping("/getcategorylist")
    public void getcategorylist(HttpServletResponse response, Model model){
        try{
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(boardService.getcategorylist());
            model.addAttribute("category" , boardService.getcategorylist());
        } catch (Exception e){}
    }

    @GetMapping("/selectcategory")
    @ResponseBody
    public void selectcategory(@RequestParam("cno") int cno){
        request.getSession().setAttribute("cno", cno);
    }


///////////////////////////////////// 메인페이지 카테고리별 게시판 호출 ////////////////////////////////
    @GetMapping("/mainboardlist")
    @ResponseBody
    public void mainboardlist(HttpServletResponse response){
        try{
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(boardService.mainboarlist() );
        }catch(Exception e){System.out.println("메인페이지게시판호출오류"+e);}
    }

    @GetMapping("/topicbest")
    @ResponseBody
    public void topicbest(HttpServletResponse response){
        try{
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(boardService.topicbest() );
        }catch(Exception e){System.out.println("메인페이지토픽베스트오류"+e);}
    }

    // 메인페이지에서 카테고리별 게시판 이동
    @GetMapping("/list/{cno}")
    public String getcategoryboardlist(@PathVariable("cno") int cno){
        System.out.println("cno테스트:"+cno);
        request.getSession().setAttribute("cno", cno);
        return "redirect:/board/list";
    }

    // 메인페이지에서 검색내용 찾기
    @GetMapping("/searchlist")
    @ResponseBody
    public void getsearchlist(HttpServletResponse response, @RequestParam("value") String value){
        try{
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(boardService.getsearchlist(value) );
        }catch(Exception e){System.out.println("메인페이지검색리스트오류"+e);}
    }

    //////////////////review에서 view.html에 게시글일부출력//////////////
    @GetMapping("/viewboard")
    @ResponseBody
    public void viewboard(@RequestParam("corpNm") String corpNm ,HttpServletResponse response){
        try{
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(boardService.viewboard(corpNm) );
        }catch (Exception e){
            System.out.println("BoardController의viewboard메소드 오류 : "+e);
            e.printStackTrace();
        }
    }
    /////////////////review에서 view.html에 게시글일부출력 END////////////

    //////////////////review에서 companyboard.html에 게시글전체출력//////////////
    @GetMapping("/companyboard")
    @ResponseBody
    public void companyboard(@RequestParam("corpNm") String corpNm ,HttpServletResponse response){
        try{
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().print(boardService.companyboard(corpNm) );
        }catch (Exception e){
            System.out.println("BoardController의companyboard메소드 오류 : "+e);
            e.printStackTrace();
        }
    }
    /////////////////review에서 companyboard.html에 게시글전체출력 END////////////

}
