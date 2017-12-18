package com.fewok.test.general;

import com.fewok.test.AbstractTestNG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.testng.annotations.Test;

/**
 * @author notreami on 17/11/23.
 */
public class TestThreadPoolTaskExecutor extends AbstractTestNG {

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Test
    public void testThreadPoolTaskExecutor() {
        System.out.println(threadPoolTaskExecutor.getPoolSize());
    }
}
