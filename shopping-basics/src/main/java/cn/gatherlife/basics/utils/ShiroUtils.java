package cn.gatherlife.basics.utils;

import cn.gatherlife.basics.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;


/**
 * @Author chy
 * @Description  shiro 相关工具类
 */
public class ShiroUtils {

    public static Session getSession(){
        return SecurityUtils.getSubject().getSession();
    }

    public static Subject getSubject(){
        return SecurityUtils.getSubject();
    }

    public static User getUser(){
        Object user = SecurityUtils.getSubject().getPrincipal();
        return user == null ? null : ((User)user);
    }

    public static void setSessionAttribute(Object key,Object value){
        getSession().setAttribute(key,value);
    }

    public static Object getSessionAttribute(Object key){
        return getSession().getAttribute(key);
    }

    public static boolean isLogin(){
        return SecurityUtils.getSubject().getPrincipal() != null;
    }

    public static void logout(){
        SecurityUtils.getSubject().logout();
    }
}
