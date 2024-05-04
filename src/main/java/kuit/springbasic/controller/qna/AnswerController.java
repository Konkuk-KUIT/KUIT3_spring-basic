package kuit.springbasic.controller.qna;

import kuit.springbasic.db.MemoryAnswerRepository;
import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class AnswerController {


    /**
     * TODO: addAnswer - @PostMapping
     * addAnswerV0 : @RequestParam, HttpServletResponse
     * addAnswerV1 : @RequestParam, Model
     * addAnswerV2 : @RequestParam, @ResponseBody
     * addAnswerV3 : @ModelAttribute, @ResponseBody
     */

    private final MemoryAnswerRepository answerRepository ;
    private final MemoryQuestionRepository questionRepository;

    @PostMapping("/api/qna/addAnswer")
    @ResponseBody
    public Answer execute(@ModelAttribute Answer answer) {
        /*Answer answer = new Answer(Long.parseLong(req.getParameter("questionId")),
                req.getParameter("author"),
                req.getParameter("contents"));
        */
        System.out.println("Answer Start");
        Answer savedAnswer = answerRepository.insert(answer);

        Question question = questionRepository.findByQuestionId(answer.getQuestionId());
        question.increaseCountOfAnswer();
        questionRepository.update(question);

        return savedAnswer;
    }

}
