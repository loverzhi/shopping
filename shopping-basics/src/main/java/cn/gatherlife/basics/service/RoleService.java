package cn.gatherlife.basics.service;

import cn.gatherlife.basics.model.Menu;
import cn.gatherlife.basics.model.Role;
import cn.gatherlife.basics.model.RoleMenu;
import cn.gatherlife.core.page.PageRequest;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * 角色管理
 * @author Weir
 * @date Oct 29, 2018
 */
public interface RoleService extends IService<Role> {

	/**
	 * 查询全部
	 * @return
	 */
	List<Role> findAll();

	/**
	 * 查询角色菜单集合
	 * @return
	 */
	List<Menu> findRoleMenus(Long roleId);

	/**
	 * 保存角色菜单
	 * @param records
	 * @return
	 */
	int saveRoleMenus(List<RoleMenu> records);

	/**
	 * 根据名称查询
	 * @param name
	 * @return
	 */
	List<Role> findByName(String name);

	/**
	 *分页查询
	 * @param pageRequest
	 * @return
	 */
	Page<Role> selectPage(PageRequest pageRequest);

}
