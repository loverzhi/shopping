package cn.gatherlife.basics.aspect;

import cn.gatherlife.basics.constants.SysConstants;
import cn.gatherlife.basics.model.Log;
import cn.gatherlife.basics.service.LogService;
import cn.gatherlife.basics.utils.HttpContextUtils;
import cn.gatherlife.basics.utils.IPUtils;
import cn.gatherlife.basics.utils.ShiroUtils;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author chy
 * @Description 系统操作日志   切面处理类  记录日志
 */
@Aspect
@Component
public class OperateLogAspect {

    @Autowired
    private LogService sysLogService;

    @Pointcut("execution(* cn.gatherlife.*.service.*.*(..))")
    public void logPointCut(){

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        long beginTime = System.currentTimeMillis();

        //执行方法
        Object result = pjp.proceed();
        //执行时长 （毫秒）
        long time = System.currentTimeMillis() - beginTime;

        //保存日志
        saveSysLog(pjp,time);
        return result;
    }

    private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
        if(ShiroUtils.getUser() == null){
            return;
        }
        String userName = ShiroUtils.getUser().getUsername();
        if(joinPoint.getTarget() instanceof LogService){
            return;
        }
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Log sysLog = new Log();

        //请求方法名
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");
        if(sysLog.getMethod().endsWith(SysConstants.FIND_BY_TOKEN)
                || sysLog.getMethod().endsWith(SysConstants.FIND_BY_TOKEN_USERID)){
            return;
        }

        //请求参数
        Object[] args = joinPoint.getArgs();
        try {
            String params = JSONObject.toJSONString(args[0]);
            if(params.length() > 200) {
                params = params.substring(0, 200) + "...";
            }
            sysLog.setParams(params);
        } catch (Exception e) {

        }

        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        // 设置IP地址
        sysLog.setIp(IPUtils.getIpAddr(request));

        // 用户名
        sysLog.setUsername(userName);

        // 执行时长(毫秒)
        sysLog.setTime(time);

        // 保存系统日志
        sysLogService.insert(sysLog);
    }
}
