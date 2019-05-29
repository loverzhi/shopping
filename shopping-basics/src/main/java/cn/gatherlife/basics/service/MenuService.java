package cn.gatherlife.basics.service;

import cn.gatherlife.basics.model.Menu;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * 菜单管理
 * @author Weir
 * @date Oct 29, 2018
 */
public interface MenuService extends IService<Menu> {

	/**
	 * 查询菜单树,用户ID和用户名为空则查询全部
	 * @param userName
	 * @param menuType 获取菜单类型，0：获取所有菜单，包含按钮，1：获取所有菜单，不包含按钮
	 * @return
	 */
	List<Menu> findTree(String userName, int menuType);

	/**
	 * 根据用户名查找菜单列表
	 * @param userName
	 * @return
	 */
	List<Menu> findByUser(String userName);
}
