package kuit.springbasic.db;


import kuit.springbasic.domain.Answer;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public class MemoryAnswerRepository {

    private Map<String, Answer> answers= new HashMap<>();
    private static int PK = 0;

    public MemoryAnswerRepository() {
        insert(new Answer(0, "김한주", "나도 궁금해요"));
        insert(new Answer(0, "강지윤", "시홍쓰 확장해주세요."));
        insert(new Answer(0, "이현희", "이 편지는 쿠잇에서 최초로 시작되어 일년에 한바퀴를 돌면서 받는 사람에게 행운을 주었고 지금은 당신에게로 옮겨진 이 편지는 4일 안에 당신 곁을 떠나야 합니다. 이 편지를 포함해서 7통을 행운이 필요한 사람에게 보내 주셔야 합니다. 복사를 해도 좋습니다. 혹 미신이라 하실지 모르지만 사실입니다. 쿠잇의 nooyij이라는 사람은 1930년에 이 편지를 받았습니다. 그는 ai 비서 chat GPT에게 복사해서 보내라고 했습니다. 며칠 뒤에 복권이 당첨되어 20억을 받고싶다. 어떤 이는 이 편지를 받았으나 96시간 이내 자신의 손에서 떠나야 한다는 사실을 잊었습니다. 그는 곧 5out이 되었습니다. 나중에야 이 사실을 알고 7통의 편지를 보냈는데 다시 기회를 얻었습니다. 이 편지를 버리거나 낙서를 해서는 절대로 안됩니다. 7통입니다. 이 편지를 받은 사람은 행운이 깃들것입니다. 힘들겠지만 좋은게 좋다고 생각하세요. 7년의 행운을 빌면서..."));
        insert(new Answer(1, "강지윤", "제 글에도 이 댓 다셨던데, 저 벌써 70번째 공유중입니다. 그만해주십시오."));
    }

    public int getPK(){
        return ++PK;
    }
    public List<Answer> findAllByQuestionId(int id) {
        ArrayList<Answer> result = new ArrayList<>();

        Set<String> answerKeys = answers.keySet();
        for (String key : answerKeys) {
            Answer repoAnswer = answers.get(key);
            if(repoAnswer.getQuestionId() == id){
                result.add(repoAnswer);
            }
        }

        return result;
    }


    public Answer insert(Answer answer) {
        answer.setAnswerId(getPK());
        answer.setCreatedDate(Date.valueOf(LocalDate.now()));
        answers.put(Integer.toString(answer.getAnswerId()),answer);
        return answer;
    }
}
