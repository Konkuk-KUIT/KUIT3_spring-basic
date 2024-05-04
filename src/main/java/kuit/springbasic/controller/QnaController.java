package kuit.springbasic.controller;

import kuit.springbasic.db.MemoryAnswerRepository;
import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class QnaController{

    private final MemoryAnswerRepository memoryAnswerRepository;
    private final MemoryQuestionRepository questionRepository;


    @RequestMapping("/qna/show")
    public String showQna(@RequestParam("questionId") int questionId, Map<String, Object> model){
        Question question = questionRepository.findByQuestionId(questionId);
        List<Answer> answers = memoryAnswerRepository.findAllByQuestionId(questionId);
        model.put("question", question);
        model.put("answers", answers);

        return "/qna/show";
    }

}

