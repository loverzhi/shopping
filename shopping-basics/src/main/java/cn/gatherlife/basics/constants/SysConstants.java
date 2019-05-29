package cn.gatherlife.basics.constants;

/**
 * 常量管理
 * @author Weir
 * @date Oct 29, 2018
 */ 
public interface SysConstants {

	/**
	 * 系统管理员用户名
	 */
	String ADMIN = "admin";
	/**
	 * 保存操作日志时排除的方法，查询token
	 */
	String FIND_BY_TOKEN = "UserTokenServiceImpl.findByToken()";
	/**
	 * 保存操作日志时排除的方法，根据token中的用户Id查询用户
	 */
	String FIND_BY_TOKEN_USERID = "UserServiceImpl.findByTokenUserId()";
	
}
