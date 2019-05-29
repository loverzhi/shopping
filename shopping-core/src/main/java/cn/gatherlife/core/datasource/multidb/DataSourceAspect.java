package cn.gatherlife.core.datasource.multidb;

import cn.gatherlife.core.datasource.core.DynamicDataSourceHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @Author chy
 * @Description 动态数据源代理
 */
@Component
@Aspect
public class DataSourceAspect implements Ordered {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${dynamic.datasource.names:slave}")
    private String dynamicDatasource;

    @Value("${dynamic.datasource.flag:slave}")
    private String dynamicDatasourceFlag;

    /**
     *  这里用来拦截有 {@link TargetDataSource}注解的方法
     **/
    @Before("@annotation(TargetDataSource)")
    public void before(JoinPoint joinPoint,TargetDataSource targetDataSource){
       try {
           String dataSourceName = targetDataSource.value().getValue();
           //判断指定的数据源类型 如果是slave 则调用LB方法  随机分配slave数据库
           if(dataSourceName.equalsIgnoreCase(dynamicDatasourceFlag)){
               dataSourceName = DBLoadBalance.getDBWithRandom(dynamicDatasource);
               //设置要使用的数据源
               DynamicDataSourceHolder.putDataSource(dataSourceName);
           } else {
               //设置要使用的数据源
               DynamicDataSourceHolder.putDataSource(dataSourceName);
           }
           logger.debug("current thread " + Thread.currentThread().getName() + " add " + dataSourceName + " to ThreadLocal");
       } catch (Exception e) {
           logger.error("current thread " + Thread.currentThread().getName() + " add data to ThreadLocal error", e);
       }
    }

    /**
     *  执行完切面后 将线程共享中的数据源名称清空
     **/
    @After("@annotation(TargetDataSource)")
    public void after(JoinPoint joinPoint){
        DynamicDataSourceHolder.removeDataSource();
    }


    /**
     * Spring Aop 执行顺序  值越小，越先执行
     * @return
     */
    @Override
    public int getOrder() {
        return 5;
    }
}
