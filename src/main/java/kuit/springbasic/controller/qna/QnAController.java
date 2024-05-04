package kuit.springbasic.controller.qna;

import kuit.springbasic.db.MemoryAnswerRepository;
import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/qna")
@RequiredArgsConstructor
@Slf4j
public class QnAController {

    private final MemoryQuestionRepository memoryQuestionRepository;
    private final MemoryAnswerRepository memoryAnswerRepository;

    @RequestMapping("/form")
    public String showQnAForm() {
        return "/qna/form";
    }

    @RequestMapping("/create")
    public String createQnA() {
        // 추후 구현
        return null;
    }

    @RequestMapping("/show")
    public ModelAndView showQnA(@RequestParam("questionId") int questionId){
        Question question = memoryQuestionRepository.findByQuestionId(questionId);
        List<Answer> answers = memoryAnswerRepository.findAllByQuestionId(questionId);
        log.info(String.valueOf(questionId));
        log.info(String.valueOf(answers));
        return new ModelAndView("/qna/show")
                .addObject("question", question)
                .addObject("answers", answers);
    }
}