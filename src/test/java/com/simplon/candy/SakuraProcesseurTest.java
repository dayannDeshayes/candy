package com.simplon.candy;

import com.simplon.candy.entity.enums.CandytagEnum;
import com.simplon.candy.service.IService.IProcessor;
import com.simplon.candy.service.Processor;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SakuraProcesseurTest {

        @Autowired
        Processor processorFactory;

        IProcessor processor;

        @BeforeEach
        void setUp() throws Exception {
            processor = processorFactory.getProcessService(CandytagEnum.SAKURA);
        }
}
