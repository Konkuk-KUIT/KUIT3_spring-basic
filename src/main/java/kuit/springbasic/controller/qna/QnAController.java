package kuit.springbasic.controller.qna;


import kuit.springbasic.db.MemoryAnswerRepository;
import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
@Controller
@RequestMapping("/qna")
public class QnAController {

    final private MemoryQuestionRepository questionRepository;
    final private MemoryAnswerRepository memoryAnswerRepository;
    @Autowired
    public QnAController(MemoryQuestionRepository QRepository,MemoryAnswerRepository ARepository) {
        this.questionRepository = QRepository;
        this.memoryAnswerRepository= ARepository;
    }

    /**
     * TODO: showQnA
     */
    @RequestMapping("/show")
    public ModelAndView showQnA(@RequestParam int questionId){
        //Long questionId = Long.parseLong(req.getParameter("questionId"));
        //System.out.println("qusetion ID"+questionId);

        Question question = questionRepository.findByQuestionId(questionId);
        List<Answer> answers = memoryAnswerRepository.findAllByQuestionId(questionId);

        ModelAndView QnA = new ModelAndView("/qna/show");
        return QnA
                .addObject("question", question)
                .addObject("answers", answers);


    }




}
