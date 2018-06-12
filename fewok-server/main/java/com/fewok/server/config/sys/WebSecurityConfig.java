package com.fewok.server.config.sys;

import com.fewok.common.common.CommonOutput;
import com.fewok.common.common.StatusInfo;
import com.fewok.common.util.JsonBinder;
import com.fewok.server.config.filter.AuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author notreami on 17/9/15.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 设置 HTTP 验证规则
     *
     * @param httpSecurity
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // 关闭csrf验证
                .csrf().disable()
                //响应安全报文头
                .headers().and()
                // 容许嵌入框架iframe跳转
                .headers().frameOptions().disable().and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 添加一个过滤器验证其他请求的Token是否合法
                .addFilterBefore(new AuthenticationFilter(), AbstractPreAuthenticatedProcessingFilter.class)
                //异常抛出，当用户请求的操作需要登录时，将抛出 AuthenticationException 异常
                .exceptionHandling()
                .authenticationEntryPoint(new WebAuthenticationEntryPoint())//需要登陆
                .accessDeniedHandler(new WebAccessDeniedHandler());//没有授权
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/")
                .antMatchers("/**/*.html")
                .antMatchers("/**/*.js")
                .antMatchers("/**/*.css")
                .antMatchers("/assets/**/*");
    }

    @Slf4j
    private static class WebAccessDeniedHandler implements AccessDeniedHandler {
        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response,
                           AccessDeniedException accessDeniedException) throws IOException, ServletException {
            log.warn("Authentication Failed: " + accessDeniedException.getMessage());

            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(JsonBinder.toJSONString(CommonOutput.createError(StatusInfo.FORBIDDEN_ERROR)));
            response.getWriter().flush();
            response.getWriter().close();
        }
    }

    @Slf4j
    private static class WebAuthenticationEntryPoint implements AuthenticationEntryPoint {
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response,
                             AuthenticationException authException) throws IOException, ServletException {
            log.warn("Authentication Failed: " + authException.getMessage());

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(JsonBinder.toJSONString(CommonOutput.createError(StatusInfo.UNAUTHORIZED_ERROR)));
            response.getWriter().flush();
            response.getWriter().close();
        }
    }
}
