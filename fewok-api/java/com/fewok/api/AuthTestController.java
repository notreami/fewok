package com.fewok.api;

import com.fewok.common.common.CommonOutput;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 授权认证测试接口
 *
 * @author notreami on 17/9/8.
 */
@Slf4j
@RestController
@RequestMapping("/api/test")
public class AuthTestController {

    @ApiOperation(value = "测试登陆接口", response = CommonOutput.class)
    @PostMapping("/login")
    public CommonOutput login() {
        return CommonOutput.createSuccess(null);
    }

    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('AUTH_WRITE') or hasRole('ADMIN')")
    public CommonOutput hello() {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        System.out.println(request.getRemoteAddr());
        System.out.println(request.getRemoteHost());
        System.out.println(request.getRemoteUser());
        System.out.println(getIpAddr(request));
        return CommonOutput.createSuccess(null);
    }

    @GetMapping("/world")
    @PreAuthorize("hasAuthority('AUTH_WRITE') and hasRole('ADMIN')")
    public CommonOutput world() {
        return CommonOutput.createSuccess(null);
    }


    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }
}
