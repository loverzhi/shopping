package cn.gatherlife.basics.utils;

import org.apache.shiro.crypto.hash.Sha256Hash;

import java.util.UUID;

/**
 * @Author chy
 * @Description 密码工具类
 */
public class PasswordUtils {

    /**
     * 匹配密码
     * @param password
     * @param salt
     * @param encryptePassword
     * @return
     */
    public static boolean match(String password,String salt,String encryptePassword){
        if(null!=password && encrypte(password,salt).equals(encryptePassword)){
            return true;
        }
        return false;
    }

    /**
     * 明文密码加密
     * @param password
     * @param salt
     * @return
     */
    public static String encrypte(String password,String salt){
        return new Sha256Hash(password,salt).toHex();
    }

    /**
     * 获取加密盐
     * @return
     */
    public static String getSalt() {
        return UUID.randomUUID().toString().replaceAll("-","").substring(0,20);
    }
}
