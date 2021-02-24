package com.smm.frame.authority.handler;

import com.alibaba.fastjson.JSONObject;
import com.smm.frame.common.response.ResponseResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jianxuan
 * @since 2019/11/27 11:10
 * 注释: 鉴权异常：认证用户访问无权限资源时的异常
 */
public class GlobalAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        responseException(response, accessDeniedException);
    }

    private void responseException(HttpServletResponse response, AccessDeniedException exception) {
        //返回的错误信息
        Map<String, Object> exceptionMap = new HashMap<>(3);
        exceptionMap.put("code", 403);
        exceptionMap.put("msg", "认证用户访问无权限资源时的异常");
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
