package cn.gatherlife.basics.aspect;

import cn.gatherlife.common.http.HttpStatus;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author chy
 * @Description  统一异常controller处理
 */
@ControllerAdvice
public class ExControllerAdvice {

    /**
     *  应用到所有的@RequestMapping 注解方法 在其执行之前初始化数据绑定器
     **/
    @InitBinder
    public void initBinder(WebDataBinder binder){}

    /**
     *  把值绑定到Model 中，使全局@RequestMapping可以获取到该值
     * @param model
     **/
    @ModelAttribute
    public void addAttributes(Model model){
        model.addAttribute("author","chendw");
    }

    /**
     * 全局异常捕捉处理
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Map errorHandler(Exception ex){
        Map map = new HashMap();
        if(ex instanceof AuthorizationException){
            map.put("code", HttpStatus.SC_FORBIDDEN);//403
        } else {
            map.put("code",HttpStatus.SC_INTERNAL_SERVER_ERROR);//500
        }
        map.put("msg",ex.getMessage());
        return map;
    }


}
