package cn.gatherlife.core.datasource.multidb;

import cn.gatherlife.core.datasource.core.DBProperties;
import cn.gatherlife.core.datasource.core.DynamicDataSourceConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({DataSourceAspect.class, DynamicDataSourceConfig.class, DBProperties.class})
public @interface EnableMultiDataSource {
}
