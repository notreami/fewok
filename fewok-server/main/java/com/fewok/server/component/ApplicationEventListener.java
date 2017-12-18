package com.fewok.server.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;

/**
 * Spring Boot 生命周期
 *
 * @author notreami on 17/1/11.
 */
@Slf4j
public class ApplicationEventListener implements ApplicationListener {

    /**
     * 在这里可以监听到Spring Boot的生命周期
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationEnvironmentPreparedEvent) {
            log.info("初始化环境变量");
        } else if (event instanceof ApplicationPreparedEvent) {
            log.info("初始化完成");
        } else if (event instanceof ContextRefreshedEvent) {
            log.info("应用刷新");
        } else if (event instanceof ApplicationReadyEvent) {
            log.info("应用已启动完成");
        } else if (event instanceof ContextStartedEvent) {
            log.info("应用启动，需要在代码动态添加监听器才可捕获");
        } else if (event instanceof ContextStoppedEvent) {
            log.info("应用停止");
        } else if (event instanceof ContextClosedEvent) {
            log.info("应用关闭");
        } else {
            log.info("其它事件:{}", event.getClass().getTypeName());
        }
    }
}
