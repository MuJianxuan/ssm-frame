package com.smm.frame.authority.handler;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jianxuan
 * @since 2019/11/27 11:11
 * 注释: 认证异常：用户在认证过程中的异常
 */
public class GlobalAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        responseException(response, authException);
    }

    private void responseException(HttpServletResponse response, AuthenticationException exception) {
        //返回的错误信息
        Map<String, Object> exceptionMap = new HashMap<>(3);
        exceptionMap.put("code", 403);
        exceptionMap.put("msg", "授权认证失败");
        exceptionMap.put("data", exception.getMessage());
        //转化成json对象
        JSONObject responseJsonObject = new JSONObject(exceptionMap);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(responseJsonObject.toString());
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
