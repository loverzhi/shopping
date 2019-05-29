package cn.gatherlife.basics.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 * @Author chy
 * @Description  跨域配置
 */
public class CorsConfig implements WebMvcConfigurer {

    private static List<String> allowHost;

    //配置允许跨域的域名
    public void setAllow(String allow){
        if(allow != null){
            allowHost = Arrays.asList(allow.split(","));
        }
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//允许跨域访问的路径
                .allowedOrigins("http://localhost:8090", "http://localhost:8091","http://localhost:8092")	// 允许跨域访问的源
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")	// 允许请求方法
                .maxAge(36000)	// 预检间隔时间
                .allowedHeaders("*")  // 允许头部设置
                .allowCredentials(true);	// 是否发送cookie
    }
}
