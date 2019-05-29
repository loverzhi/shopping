package cn.gatherlife.basics.interceptor;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @Author chy
 * @Description
 */
@Log4j2
@Configuration
@ConfigurationProperties(prefix = "spring.cross")
public class CrossInterceptor implements HandlerInterceptor {

    private static List<String> allowHosts;

    //配置允许跨域的域名
    public void setAllow(String allow){
        if(allow != null){
            allowHosts = Arrays.asList(allow.split(","));
        }
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String origin = request.getHeader(HttpHeaders.ORIGIN);
        if(origin != null){
            if (allowHosts.contains(origin)) {
                response.setHeader("Access-Control-Allow-Origin", origin);
                response.setHeader("Access-Control-Allow-Credentials", "true");
                response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT, HEAD");
                response.setHeader("Access-Control-Allow-Headers", "content-type,token");
                response.setHeader("Access-Control-Max-Age", "36000");
            } else {
                log.warn("跨域失败：" + origin);
            }
        }
        return true;
    }
}
