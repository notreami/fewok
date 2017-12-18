package com.fewok;

import com.fewok.dsl.util.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author notreami on 17/9/8.
 */
@SpringBootApplication
@Slf4j
@EnableAsync
@EnableRetry
@EnableScheduling
@EnableAspectJAutoProxy
@ServletComponentScan
public class FewOKApplication {

    public static void main(String[] args) {
        IdWorker.getInstance(0, 0);//初始化唯一id生成器
        SpringApplication.run(FewOKApplication.class, args);
    }
}