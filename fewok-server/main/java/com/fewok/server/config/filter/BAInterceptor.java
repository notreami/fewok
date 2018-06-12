package com.fewok.server.config.filter;

import com.fewok.common.common.CommonOutput;
import com.fewok.common.common.StatusInfo;
import com.fewok.common.util.JsonBinder;
import com.fewok.dsl.util.BasicAuth;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * BA认证
 *
 * @author notreami on 16/8/8.
 */
@Slf4j
public class BAInterceptor extends HandlerInterceptorAdapter {
    /**
     * 拦截的url通配符
     */
    private static final String URL_PATTERNS = "/api";

    @Value("${custom.mws_client_id}")
    private String clientId;
    @Value("${custom.mws_client_secret}")
    private String clientSecret;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        final String servletPath = request.getServletPath();

        if (!StringUtils.startsWith(servletPath, URL_PATTERNS)) {
            return true;
        }
        //测试环境可方便调试
        if (StringUtils.isEmpty(clientId) || StringUtils.isEmpty(clientSecret)) {
            return true;
        }

        BasicAuth.Data basicAuthData = null;
        String requestURI = null;
        String method = null;
        try {
            requestURI = request.getRequestURI();
            method = request.getMethod();

            String date = request.getHeader("Date");
            String authorization = request.getHeader("Authorization");
            basicAuthData = BasicAuth.Data.create(date, authorization);
            if (StringUtils.isNotEmpty(date) && StringUtils.isNotEmpty(authorization)) {
                String clientKey = BasicAuth.getClientKey(basicAuthData.getAuth());//若client_id错误,则无需加密验证(加密耗时),指下一行代码
                if (StringUtils.equals(clientId, clientKey) && BasicAuth.checkBasicAuthData(basicAuthData, clientSecret, method, requestURI)) {
                    return true;
                }
            }
        } catch (Exception e) {
            log.error("BA exception: " + e);
        }
        log.warn("BA failed: " + basicAuthData + " " + method + " " + requestURI);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(JsonBinder.toJSONString(CommonOutput.createError(StatusInfo.FORBIDDEN_ERROR)));
        response.getWriter().flush();
        response.getWriter().close();
        return false;
    }

}
