package com.fewok.server;

import com.fewok.api.UnAuthTestController;
import com.fewok.common.util.JsonBinder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 未授权认证工具接口
 * @author notreami on 17/11/13.
 */
@Slf4j
@RestController
@RequestMapping("/test/reset")
public class TestController {

    @Autowired
    private UnAuthTestController unAuthTestController;
    @GetMapping("/test")
    public String test(){
       return JsonBinder.toJSONString(unAuthTestController.activeStatus());
//        return "1232132";
    }
}
