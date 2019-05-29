package cn.gatherlife.core.datasource.multidb;

import cn.gatherlife.core.datasource.DataSourceEnum;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
@Documented
@Inherited
public @interface TargetDataSource {
    //此处接收的是数据源的名称
    DataSourceEnum value();
}
