package com.fewok.server.config.filter;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * 请求拦截（token），过滤出账户锁定用户,匿名用户
 *
 * @author notreami on 17/10/29.
 */
public class AuthenticationFilter extends GenericFilterBean {

    /**
     * 拦截的url通配符
     */
    private static final String URL_PATTERNS = "/api";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
        final String servletPath = ((HttpServletRequest) request).getServletPath();

        if (StringUtils.startsWith(servletPath, URL_PATTERNS)) {
            //        Authentication authentication = TokenAuthenticationService.getAuthentication((HttpServletRequest)request);

            List<GrantedAuthority> authorities = Lists.newArrayList();
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//            authorities.add(new SimpleGrantedAuthority("AUTH_WRITE"));
            // 生成令牌
            Authentication authentication = new PreAuthenticatedAuthenticationToken("admin", "123456", authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }


        chain.doFilter(request, response);
    }
}
