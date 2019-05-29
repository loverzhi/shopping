package cn.gatherlife.basics.service;

import cn.gatherlife.basics.model.UserToken;
import com.baomidou.mybatisplus.service.IService;

/**
 * 用户Token管理
 * @author Weir
 * @date Aug 21, 2018
 */
public interface UserTokenService extends IService<UserToken> {

	/**
	 * 根据用户id查找
	 * @param userId
	 * @return
	 */
	UserToken findByUserId(Long userId);

	/**
	 * 根据token查找
	 * @param token
	 * @return
	 */
	UserToken findByToken(String token);
	
	/**
	 * 生成token
	 * @param userId
	 * @return
	 */
	UserToken createToken(long userId);

}
