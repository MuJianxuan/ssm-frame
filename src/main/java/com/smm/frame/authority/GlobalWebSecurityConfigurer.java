package com.smm.frame.authority;

import com.smm.frame.authority.filter.GlobalBasicAuthenticationFilter;
import com.smm.frame.authority.filter.GlobalUsernamePasswordAuthenticationFilter;
import com.smm.frame.authority.handler.GlobalAccessDeniedHandler;
import com.smm.frame.authority.handler.GlobalAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author jianxuan
 * @since 2019/11/27 9:21
 * 注释: 安全权限控制类
 * 拦截器和过滤器的区别：
 * 1 、拦截器是基于java的反射机制的，而过滤器是基于函数回调。
 * <p>
 * 2 、拦截器不依赖与servlet容器，过滤器依赖与servlet容器。
 * <p>
 * 3 、拦截器只能对action请求起作用，而过滤器则可以对几乎所有的请求起作用。
 * <p>
 * 4 、拦截器可以访问action上下文、值栈里的对象，而过滤器不能访问。
 * <p>
 * 5 、在action的生命周期中，拦截器可以多次被调用，而过滤器只能在容器初始化时被调用一次
 */
public class GlobalWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    private GlobalAuthenticationEntryPoint authenticationEntryPoint = new GlobalAuthenticationEntryPoint();

    private GlobalAccessDeniedHandler accessDeniedHandler = new GlobalAccessDeniedHandler();

    /**
     * Rsa私钥
     */
    protected String privateKey = null;

    /**
     * 是否关闭权限认证 子类覆盖即修改
     */
    protected boolean isCloseAuth = false;

    /**
     * 密码加密器
     *
     * @return
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * web资源权限控制
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.ignoring().antMatchers("/config/**", "/css/**", "/img/**", "/js/**");
        //swagger-ui start
        web.ignoring().antMatchers("/v2/api-docs/**");
        web.ignoring().antMatchers("swagger-ui.html");
        web.ignoring().antMatchers("/swagger.json");
        web.ignoring().antMatchers("/swagger-resources/**");
        web.ignoring().antMatchers("/webjars/**");
        //swagger-ui end
    }

    /**
     * http请求权限控制
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (isCloseAuth) {
            closeAuthConfigure(http);
        } else {
            customConfigure(http);
            commonConfigure(http);
        }

    }

    /**
     * 共用的权限控制
     *
     * @param http
     */
    private void commonConfigure(HttpSecurity http) throws Exception {
        GlobalUsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter = new GlobalUsernamePasswordAuthenticationFilter(authenticationManager(), privateKey);
        GlobalBasicAuthenticationFilter basicAuthenticationFilter = new GlobalBasicAuthenticationFilter(authenticationManager());
        //禁用SESSION、JSESSIONID
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //添加登录过滤过滤器
        http.addFilter(usernamePasswordAuthenticationFilter).addFilter(basicAuthenticationFilter);
        //添加权限异常拦截器
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint);
    }

    /**
     * 自定义权限控制
     *
     * @param http
     * @return
     */
    protected HttpSecurity customConfigure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .anyRequest().authenticated()
                .and();
        return http;
    }

    /**
     * 关闭接口权限校验配置
     *
     * @param http
     * @throws Exception
     */
    private void closeAuthConfigure(HttpSecurity http) throws Exception {
        //关闭跨域限制且允许所有请求访问，关闭拦截器和过滤器
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/**").permitAll();
    }
}
