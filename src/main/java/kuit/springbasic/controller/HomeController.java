package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemoryQuestionRepository memoryQuestionRepository;

    @RequestMapping("/homeV1")
    public ModelAndView showHomeV1() {
        log.info("HomeController.homeV1");

        ModelAndView modelAndView = new ModelAndView("home");

        List<Question> questions = memoryQuestionRepository.findAll();
        modelAndView.addObject("questions", questions);

        return modelAndView;
    }

    @RequestMapping("/homeV2")
    public String showHomeV2(Model model) {
        log.info("HomeController.homeV2");

        List<Question> questions = memoryQuestionRepository.findAll();
        model.addAttribute("questions", questions);

        return "home";
    }

    @GetMapping("/")
    public ModelAndView showHomeV3() {
        log.info("HomeController");

        ModelAndView modelAndView = new ModelAndView("home");

        List<Question> questions = memoryQuestionRepository.findAll();
        modelAndView.addObject("questions", questions);

        return modelAndView;
    }

}
