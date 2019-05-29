package cn.gatherlife.core.datasource.core;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author chy
 * @Description 动态数据源配置
 */
@Configuration
public class DynamicDataSourceConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DBProperties properties;

    @Value("${dynamic.default-db:master}")
    private String dynamicDefaultDB;

    @Bean(name = "dataSource")
    public DataSource dataSource(){
        //按照目标配置数据源名称和目标数据源对象的映射存放在Map中
        Map<Object, Object> targetDataSources = new HashMap<>();
        //获取配置文件中的数据源
        Map<String,HikariDataSource> hikari = properties.getHikari();
        Set<String> keys = hikari.keySet();
        HikariDataSource hikariDataSource = null;
        HikariDataSource masterDataSource = null;

        String poolName = "";
        for(String key : keys ){
            hikariDataSource = hikari.get(key);
            poolName = hikariDataSource.getPoolName();
            targetDataSources.put(hikariDataSource.getPoolName(),hikariDataSource);
            if(poolName.equalsIgnoreCase(dynamicDefaultDB)){
                masterDataSource = hikariDataSource;
            }
        }

        //采用思想AbstractRoutingDataSource的对象包装多数据源
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);
        //设置默认的数据源 当拿不到数据源是，使用此配置
        if(null != masterDataSource){
            dataSource.setDefaultTargetDataSource(masterDataSource);
        } else {
            logger.error("Can't find master db, project will be exit");
            System.exit(0);
        }
        return dataSource;
    }


    @Bean
    public PlatformTransactionManager txManager(){
        return new DataSourceTransactionManager(dataSource());
    }
}
