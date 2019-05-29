package cn.gatherlife.basics.service;

import cn.gatherlife.basics.model.User;
import cn.gatherlife.basics.model.UserRole;
import cn.gatherlife.core.page.PageRequest;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Set;

/**
 * 用户管理
 * @author Weir
 * @date Oct 29, 2018
 */
public interface UserService extends IService<User> {



	/**
	 * 根据用户名查询用户
	 * @param username
	 * @return
	 */
	User findByUsername(String username);

	/**
	 * 查询用户信息，权限认证是专业
	 * @param userId
	 * @return
	 */
	User findByTokenUserId(Long userId);

	/**
	 * 查找用户的菜单权限标识集合
	 * @param userName
	 * @return
	 */
	Set<String> findPermissions(String userName);

	/**
	 * 查找用户的角色集合
	 * @param userId
	 * @return
	 */
	List<UserRole> findUserRoles(Long userId);

	/**
	 *分页查询
	 * @param pageRequest
	 * @return
	 */
	Page<User> selectPage(PageRequest pageRequest);

}
