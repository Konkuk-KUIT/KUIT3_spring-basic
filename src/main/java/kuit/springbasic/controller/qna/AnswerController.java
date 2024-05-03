package kuit.springbasic.controller.qna;

import kuit.springbasic.db.MemoryAnswerRepository;
import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AnswerController {

    private final MemoryQuestionRepository memoryQuestionRepository;
    private final MemoryAnswerRepository memoryAnswerRepository;
    /**
     * TODO: addAnswer - @PostMapping
     * addAnswerV0 : @RequestParam, HttpServletResponse
     * addAnswerV1 : @RequestParam, Model
     * addAnswerV2 : @RequestParam, @ResponseBody
     * addAnswerV3 : @ModelAttribute, @ResponseBody
     */
    @PostMapping("/api/qna/addAnswer")
    public ResponseEntity<Answer> addAnswer(@RequestParam("questionId") int questionId,
                                    @RequestParam("writer") String author,
                                    @RequestParam("contents") String contents
                                    ){
        Answer answer = new Answer(questionId, author, contents);
        Answer savedAnswer = memoryAnswerRepository.insert(answer);

        Question question = memoryQuestionRepository.findByQuestionId(answer.getQuestionId());
        question.increaseCountOfAnswer();
        memoryQuestionRepository.update(question);

        return new ResponseEntity<>(savedAnswer, HttpStatus.OK);

    }
    @GetMapping("/qna/form")
    public String showQnaForm(){
        return "/qna/form";
    }

    @GetMapping("/qna/show")
    public ModelAndView showQna(@RequestParam("questionId") String questionDump){
        ModelAndView mv=new ModelAndView();

        int questionId = Integer.parseInt(questionDump);
        Question question = memoryQuestionRepository.findByQuestionId(questionId);
        List<Answer> answers = memoryAnswerRepository.findAllByQuestionId(questionId);

        mv.addObject("question",question);
        mv.addObject("answers",answers);
        mv.setViewName("/qna/show");
        return mv;
    }
}
