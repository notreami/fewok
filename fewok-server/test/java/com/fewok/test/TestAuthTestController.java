package com.fewok.test;

import com.fewok.api.AuthTestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.testng.annotations.Test;

/**
 * @author notreami on 17/9/9.
 */
@Slf4j
public class TestAuthTestController extends AbstractTestNG {
    @Autowired
    private AuthTestController authTestController;

    @Value("${spring.profiles.active}")
    private String projectEnv;

    @Test
    public void sss() {
        System.out.println(projectEnv);
    }

}
