package cn.gatherlife.core.datasource.multidb;

import java.util.Random;

/**
 * @Author chy
 * @Description 定义获取DataSource的LB方法
 */
public class DBLoadBalance {

    /**
     *  随机获取db
     **/
    public static String getDBWithRandom(String dbs){
        String [] dynamicDBs = dbs.split(",");
        int num = new Random().nextInt(dynamicDBs.length);
        return dynamicDBs[num];
    }
}
