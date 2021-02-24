package com.smm.frame.authority.filter;

import com.alibaba.fastjson.JSONObject;
import com.smm.frame.authority.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jianxuan
 * @since 2019/11/27 10:53
 * 注释: 权限登录过滤器
 */
@Slf4j
public class GlobalUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    /**
     * Rsa的私钥
     */
    private String privateRsaKey;

    public GlobalUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager, String privateRsaKey) {
        this.authenticationManager = authenticationManager;
        this.privateRsaKey = privateRsaKey;
    }

    /**
     * 验证token
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = this.obtainUsername(request);
        String password = this.obtainPassword(request);
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>())
        );
    }

    /**
     * 成功验证
     *
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //创建token
        String token = JwtUtil.createToken(((User) authResult.getPrincipal()).getUsername());
        //输出给前端
        returnToken(token, response);
    }

    private void returnToken(String token, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>(3);
        map.put("code", 200);
        map.put("msg", "");
        map.put("data", token);
        JSONObject jsonObject = new JSONObject(map);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(jsonObject.toString());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
