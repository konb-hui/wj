package com.konb.wj;

import com.konb.wj.service.ChangeBatchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
public class BatchTest {

    @Autowired
    private ChangeBatchService changeBatchService;

    @Test
    void MyTest() {
        Set<String> teams = this.changeBatchService.listAllKindTeams();
        System.out.println(teams);
    }

}
