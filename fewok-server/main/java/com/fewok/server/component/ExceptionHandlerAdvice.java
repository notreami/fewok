package com.fewok.server.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author notreami on 17/1/14.
 */
@Slf4j
public class ExceptionHandlerAdvice {

    @ExceptionHandler(value = Exception.class)
    public ModelAndView exception(Exception e, WebRequest request) {
        log.error("外抛异常,e=", e);

        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", "服务器异常,请重试");
        return modelAndView;
    }
}
