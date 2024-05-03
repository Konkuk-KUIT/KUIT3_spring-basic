package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor //final field 들 찾아서 자동 생성자 생성
// final 없으면 생성자 인자로 안받아주나? -> Yes only for final, non-null fields
public class HomeController {

    private final MemoryQuestionRepository memoryQuestionRepository;

    @GetMapping("/homeV1")
//    @RequestMapping("/")
    public ModelAndView showHomeV1(HttpServletRequest request, HttpServletResponse response) {
        log.info("HomeController.homeV1");

        ModelAndView modelAndView = new ModelAndView("home");
        //내부에서 직접 ModelAndView 생성
        List<Question> questions = memoryQuestionRepository.findAll();
        modelAndView.addObject("questions", questions);

        return modelAndView;
    }

    @GetMapping("/homeV2")
//    @RequestMapping("/")
    public String showHomeV2(Map<String, Object> model) {
        //TODO 인자가 Map 일 때는 어떤 식으로 지원하는건지? Spring의 HandlerAdapter, HandlerMethodArgumentResolver 설명 찾아보기
        log.info("HomeController.homeV2");

        List<Question> questions = memoryQuestionRepository.findAll();
        model.put("questions", questions);

        return "home"; //viewResolver 이용해 중복되는 suffix 넣어줘 수정 용이, 중복 삭제
    }

    @GetMapping("/")
    public String showHomeV3(Model model) {
        log.info("HomeController.homeV3");

        List<Question> questions = memoryQuestionRepository.findAll();
        model.addAttribute("questions", questions);

        return "home";
    }

}
