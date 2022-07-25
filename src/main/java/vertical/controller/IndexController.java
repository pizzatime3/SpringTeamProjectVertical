package vertical.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/")
    public String index(Model model){
        return "main";
    }

    @GetMapping("/mainsearch/{input}")
    public String mainsearch(@PathVariable("input") String input, Model model){
        model.addAttribute("input", input);
        System.out.println("input : " + input);
        return "mainsearch";
    }

    @GetMapping("/mainsearch")
    public String mainsearch1(Model model){
//        System.out.println(model.getAttribute("input"));
        return "mainsearch";
    }
//    @GetMapping("/mainsearch/input")
//    public String mainsearch(@RequestParam("input") String input, Model model){
//        System.out.println("input : " + input);
//        request.getSession().setAttribute("input", input);
//        return "/mainsearch";
//    }
//
//    @GetMapping("/mainsearch")
//    public String mainsearchpage(Model model){
//        String input = (String)request.getSession().getAttribute("input");
//        model.addAttribute(input);
//        return
//    }

}
