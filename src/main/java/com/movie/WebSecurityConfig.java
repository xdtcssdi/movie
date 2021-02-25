package com.movie;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Configuration
public class WebSecurityConfig extends WebMvcConfigurerAdapter {


    @Bean
    public SecurityInterceptor getSecurityInterceptor() {
        return new SecurityInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());

        // 排除配置
//        addInterceptor.excludePathPatterns("/user/login");
//        addInterceptor.excludePathPatterns("/register");

        // 拦截配置
        addInterceptor.addPathPatterns("/**");
        addInterceptor.excludePathPatterns("/loginPage");
        addInterceptor.excludePathPatterns("/mainPage");
        addInterceptor.excludePathPatterns("/static/**");
        addInterceptor.excludePathPatterns("/upload/**");
        addInterceptor.excludePathPatterns("/");
        addInterceptor.excludePathPatterns("/movie/**");
        addInterceptor.excludePathPatterns("/user/login");
        addInterceptor.excludePathPatterns("/user/register");
    }

    private class SecurityInterceptor extends HandlerInterceptorAdapter {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
            System.out.println("拦截" + request.getRequestURI());
            HttpSession session = request.getSession();
            if (session.getAttribute("user") != null) {
                return true;
            }
            // 跳转登录
            String url = "/loginPage";
            response.sendRedirect(url);
            return false;
        }

        @Override
        public void afterCompletion(
                HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
                throws Exception {
            System.out.println("拦截" + request.getRequestURI());
            HttpSession session = request.getSession();
            if (session.getAttribute("user") == null) {
                // 跳转登录
                String url = "/loginPage";
                response.sendRedirect(url);
            }
        }

    }
}
