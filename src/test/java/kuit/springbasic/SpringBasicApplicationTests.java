package kuit.springbasic;

import kuit.springbasic.db.MemoryAnswerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBasicApplicationTests {
    @Autowired
    MemoryAnswerRepository memoryAnswerRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void getAllBean() {
        System.out.println(memoryAnswerRepository.getApplicationContext().getBean("springBasicApplication"));
    }
}
