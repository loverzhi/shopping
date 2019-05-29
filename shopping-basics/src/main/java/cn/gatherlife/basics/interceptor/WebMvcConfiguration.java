package cn.gatherlife.basics.interceptor;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Author chy
 * @Description
 */
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(new CrossInterceptor()).addPathPatterns("/**");
       // log.debug("跨域拦截器注册成功！");
    }


}
