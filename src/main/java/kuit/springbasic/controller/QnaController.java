package kuit.springbasic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class QnaController {

    @RequestMapping("/qna/show")
    public String showQna(){
        return "/qna/show";
    }

}
