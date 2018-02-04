package com.fewok.api;

import com.fewok.common.common.ClientInfo;
import com.google.common.collect.Maps;
import com.fewok.biz.service.SimpleService;
import com.fewok.common.common.CommonOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/**
 * 未授权认证测试接口
 *
 * @author notreami on 17/9/8.
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class UnAuthTestController {

    @Value("${spring.profiles.active}")
    private String projectEnv;

    @Autowired
    private SimpleService simpleService;

    @GetMapping("/status/active")
    public CommonOutput activeStatus() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        String datetime = zonedDateTime.getZone().getDisplayName(TextStyle.FULL, Locale.ROOT)
                + "(" + zonedDateTime.getZone().getDisplayName(TextStyle.SHORT, Locale.ROOT) + ")  "
                + zonedDateTime;


        Map<String, Object> objectMap = Maps.newHashMap();
        objectMap.put("当前环境", projectEnv);
        objectMap.put("服务器时区", datetime);
        objectMap.put("数据库连接测试", simpleService.selectSimpleDomainByAll());
        objectMap.put("数据库日期", simpleService.selectSysDate());
        return CommonOutput.createSuccess(objectMap);
    }

    @GetMapping({"/**/*.html"})
    public String testHold(HttpServletRequest request) {
        return request.getServletPath();
    }

    @GetMapping("/freemarker1")
    public ModelAndView freemarker1(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("freemarker");
        modelAndView.addObject("placeholder","占位");

        ClientInfo clientInfo=ClientInfo.builder()
                .clientAppKey("test clientAppKey")
                .clientIp("test if")
                .platform(ClientInfo.Platform.IPHONE)
                .originate(ClientInfo.Originate.FT)
                .uuid(UUID.randomUUID().toString())
                .build();
        modelAndView.addObject(clientInfo);
        return modelAndView;
    }

    @GetMapping("/freemarker2")
    public String freemarker2(Model model){
        return "freemarker";
    }
}
