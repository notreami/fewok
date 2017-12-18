package com.fewok.test.operation;

import com.fewok.biz.service.SimpleService;
import com.fewok.test.AbstractTestNG;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * @author notreami on 17/9/10.
 */
public class TestSimpleService extends AbstractTestNG {
    @Autowired
    private SimpleService simpleService;

    @Test
    public void testSimpleService(){
        System.out.println(simpleService.selectSysDate());
    }
}
