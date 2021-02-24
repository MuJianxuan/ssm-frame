package com.smm.frame.authority.filter;

import com.smm.frame.authority.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author jianxuan
 * @since 2019/11/27 10:56
 * 注释: token校验过滤  是否存在token 存在说明需要验证  不存在说明是首次请求放行
 */
public class GlobalBasicAuthenticationFilter extends BasicAuthenticationFilter {

    public GlobalBasicAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /**
     * 过滤登录信息
     *
     * @param request
     * @param response
     * @param chain    处理request和response
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(JwtUtil.TOKEN_HEADER);
        //判断header是否为空 是否是以规定的字符串开头
        if (StringUtils.isEmpty(header) || !header.startsWith(JwtUtil.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        //解析token并设置认证信息
        SecurityContextHolder.getContext().setAuthentication(getUsernamePasswordAuthenticationToken(header));
        chain.doFilter(request, response);
    }


    /**
     * 获取用户信息
     *
     * @param header
     * @return
     */
    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(String header) {
        String token = header.replace(JwtUtil.TOKEN_PREFIX, "");
        //获取用户信息
        String username = JwtUtil.getSubject(token);
        if (!StringUtils.isEmpty(username)) {
            return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
        }
        throw new RuntimeException("[解析token] 用户授权失败");
    }
}
