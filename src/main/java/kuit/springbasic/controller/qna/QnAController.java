package kuit.springbasic.controller.qna;

import kuit.springbasic.db.MemoryAnswerRepository;
import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QnAController {

    private final MemoryQuestionRepository memoryQuestionRepository;
    private final MemoryAnswerRepository memoryAnswerRepository;

    @RequestMapping("/form")
    public String showQnAForm() {
        log.info("QnAController.showQnAForm");
        return "/qna/form";
    }

    @RequestMapping(value = "/show", params = "questionId")
    public ModelAndView showQnA(@RequestParam("questionId") int questionId) {
        log.info("QnAController.showQnA");
        ModelAndView mav = new ModelAndView("qna/show");
        mav.addObject("question", memoryQuestionRepository.findByQuestionId(questionId)).
                addObject("answers", memoryAnswerRepository.findAllByQuestionId(questionId));
        return mav;
    }

    @PostMapping("/create")
    public String createQnA(@ModelAttribute Question newQuestion) {
        log.info("QnAController.createQnA");
        memoryQuestionRepository.insert(newQuestion);
        return "redirect:/";
    }

    /**
     * TODO: showQnA
     */

}
