package cn.gatherlife.core.datasource.multidb;

import cn.gatherlife.core.datasource.core.DynamicDataSourceHolder;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @Author chy
 * @Description 数据源读写分离
 */
@Component
@Aspect
public class SplitDataSourceAspect implements Ordered {

    private final static Logger log= LoggerFactory.getLogger(SplitDataSourceAspect.class);

    @Value("${dynamic.datasource.names:slave}")
    private String dynamicDatasource;

    @Pointcut("execution(* cn.gatherlife.*.mapper.*.select*(..)) " +
            "|| execution(* cn.gatherlife.*.mapper.*.find*(..)) " +
            "|| execution(* cn.gatherlife.*.mapper.*.get*(..))")
    public void toSlaveDataSource(){
        
    }
    
    @Before("toSlaveDataSource()")
    public void beforeSlaveDataSource(){
        try {
            String dataSourceName = DBLoadBalance.getDBWithRandom(dynamicDatasource);
            DynamicDataSourceHolder.putDataSource(dataSourceName);
            log.debug("current thread " + Thread.currentThread().getName() + " add " + dataSourceName + " to ThreadLocal");
        } catch (Exception e) {
            log.error("current thread " + Thread.currentThread().getName() + " add data to ThreadLocal error", e);
        }
    }

    @After("toSlaveDataSource()")
    public void afterSlaveDataSource() {
        try {
            DynamicDataSourceHolder.removeDataSource();
            log.debug("current thread " + Thread.currentThread().getName() + " remove " + dynamicDatasource + " to ThreadLocal");
        } catch (Exception e) {
            log.error("current thread " + Thread.currentThread().getName() + " remove data to ThreadLocal error", e);
        }
    }

    /**
     * spring aop 执行顺序 值越小  越先执行
     **/
    @Override
    public int getOrder() {
        return 100;
    }
}
