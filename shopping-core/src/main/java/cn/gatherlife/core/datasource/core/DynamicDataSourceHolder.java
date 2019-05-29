package cn.gatherlife.core.datasource.core;

/**
 * @Author chy
 * @Description 动态数据源配置
 */
public class DynamicDataSourceHolder {

    private static String USEFUL_DB = null;

    /**
     * 本地线程共享变量
     **/
    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();


    public static void putDataSource(String name){
        THREAD_LOCAL.set(name);
    }

    public static String getDataSource(){
        if(null != USEFUL_DB){
            return USEFUL_DB;
        }
        return THREAD_LOCAL.get();
    }

    public static void removeDataSource(){
        THREAD_LOCAL.remove();
    }
}
