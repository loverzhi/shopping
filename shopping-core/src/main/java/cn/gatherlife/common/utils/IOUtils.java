package cn.gatherlife.common.utils;

import java.io.Closeable;

/**
 * @Author chy
 * @Description IO 相关工具类
 */
public class IOUtils {

    public static void closeQuietly(final Closeable closeable){
        try {
        	if(closeable != null){
        	    closeable.close();
            }
        } catch (Exception e) {

        }
    }
}
