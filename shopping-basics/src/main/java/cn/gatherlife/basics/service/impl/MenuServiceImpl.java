package cn.gatherlife.basics.service.impl;

import cn.gatherlife.basics.constants.SysConstants;
import cn.gatherlife.basics.mapper.MenuMapper;
import cn.gatherlife.basics.model.Menu;
import cn.gatherlife.basics.service.MenuService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

	@Override
	public boolean insertOrUpdate(Menu record) {
		if(record.getId() == null || record.getId() == 0) {
			return retBool(this.baseMapper.insert(record));
		}
		if(record.getParentId() == null) {
			record.setParentId(0L);
		}
		return retBool(this.baseMapper.update(record,
				new EntityWrapper<Menu>().eq("id", record.getId())));
	}
/*
	@Override
	public int delete(Menu record) {
		return this.baseMapper.deleteById(record.getId());
	}

	@Override
	public int delete(List<Menu> records) {
		for(Menu record:records) {
			delete(record);
		}
		return 1;
	}

	@Override
	public Menu selectById(Long id) {
		return this.baseMapper.selectById(id);
	}*/
	
	@Override
	public List<Menu> findTree(String userName, int menuType) {
		List<Menu> sysMenus = new ArrayList<>();
		List<Menu> menus = findByUser(userName);
		for (Menu menu : menus) {
			if (menu.getParentId() == null || menu.getParentId() == 0) {
				menu.setLevel(0);
				sysMenus.add(menu);
			}
		}
		sysMenus.sort((o1, o2) -> o1.getOrderNum().compareTo(o2.getOrderNum()));
		findChildren(sysMenus, menus, menuType);
		return sysMenus;
	}

	@Override
	public List<Menu> findByUser(String userName) {
		if(StringUtils.isBlank(userName) || SysConstants.ADMIN.equalsIgnoreCase(userName)) {
			return this.baseMapper.selectList(new EntityWrapper<Menu>());
		}
		return this.baseMapper.selectByUserName(userName);
	}

	private void findChildren(List<Menu> SysMenus, List<Menu> menus, int menuType) {
		for (Menu SysMenu : SysMenus) {
			List<Menu> children = new ArrayList<>();
			for (Menu menu : menus) {
				if(menuType == 1 && menu.getType() == 2) {
					// 如果是获取类型不需要按钮，且菜单类型是按钮的，直接过滤掉
					continue ;
				}
				if (SysMenu.getId() != null && SysMenu.getId().equals(menu.getParentId())) {
					menu.setParentName(SysMenu.getName());
					menu.setLevel(SysMenu.getLevel() + 1);
					children.add(menu);
				}
			}
			SysMenu.setChildren(children);
			children.sort((o1, o2) -> o1.getOrderNum().compareTo(o2.getOrderNum()));
			findChildren(children, menus, menuType);
		}
	}
	
}
