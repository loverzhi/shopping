package cn.gatherlife.basics.service.impl;

import cn.gatherlife.basics.constants.SysConstants;
import cn.gatherlife.basics.mapper.MenuMapper;
import cn.gatherlife.basics.mapper.RoleMapper;
import cn.gatherlife.basics.mapper.RoleMenuMapper;
import cn.gatherlife.basics.model.Menu;
import cn.gatherlife.basics.model.Role;
import cn.gatherlife.basics.model.RoleMenu;
import cn.gatherlife.basics.service.RoleService;
import cn.gatherlife.core.page.PageRequest;
import cn.gatherlife.core.utils.Constant;
import cn.gatherlife.core.utils.Query;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

	@Autowired
	private RoleMenuMapper roleMenuMapper;
	@Autowired
	private MenuMapper menuMapper;

	/*@Override
	public int insert(Role record) {
		if(record.getId() == null || record.getId() == 0) {
			return this.baseMapper.insert(record);
		}
		return this.baseMapper.update(record,
				new EntityWrapper<Role>().eq("id", record.getId()));
	}

	@Override
	public int delete(Role record) {
		return this.baseMapper.deleteById(record.getId());
	}

	@Override
	public int delete(List<Role> records) {
		for(Role record:records) {
			delete(record);
		}
		return 1;
	}

	@Override
	public Role selectById(Long id) {
		return this.baseMapper.selectById(id);
	}
*/
	@Override
	public Page selectPage(PageRequest pageRequest) {
		String name = pageRequest.getColumnFilter("name").getValue();

		Page<Role> page = this.selectPage(
				new Query<Role>(pageRequest).getPage(),
				new EntityWrapper<Role>()
						.like(StringUtils.isNotBlank(name), "name", name)
						.addFilterIfNeed(pageRequest.getColumnFilters().get(Constant.SQL_FILTER) != null,
								pageRequest.getColumnFilters().get(Constant.SQL_FILTER))
		);
		return page;
	}

	@Override
	public List<Role> findAll() {
		return this.baseMapper.selectList(new EntityWrapper<Role>());
	}

	@Override
	public List<Menu> findRoleMenus(Long roleId) {
		Role role = this.baseMapper.selectById(roleId);
		if(SysConstants.ADMIN.equalsIgnoreCase(role.getName())) {
			// 如果是超级管理员，返回全部
			return menuMapper.selectList(new EntityWrapper<Menu>());
		}
		return menuMapper.selectRoleMenus(roleId);
	}

	@Transactional
	@Override
	public int saveRoleMenus(List<RoleMenu> records) {
		if(records == null || records.isEmpty()) {
			return 1;
		}
		Long roleId = records.get(0).getRoleId(); 
		roleMenuMapper.delete(new EntityWrapper<RoleMenu>().eq("role_id", roleId));
		for(RoleMenu record:records) {
			roleMenuMapper.insert(record);
		}
		return 1;
	}

	@Override
	public List<Role> findByName(String name) {
		return this.baseMapper.selectList(new EntityWrapper<Role>().eq("name", name));
	}
	
}
