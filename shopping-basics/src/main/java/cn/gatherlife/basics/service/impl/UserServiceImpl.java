package cn.gatherlife.basics.service.impl;

import cn.gatherlife.basics.mapper.DeptMapper;
import cn.gatherlife.basics.mapper.RoleMapper;
import cn.gatherlife.basics.mapper.UserMapper;
import cn.gatherlife.basics.mapper.UserRoleMapper;
import cn.gatherlife.basics.model.Menu;
import cn.gatherlife.basics.model.Role;
import cn.gatherlife.basics.model.User;
import cn.gatherlife.basics.model.UserRole;
import cn.gatherlife.basics.service.MenuService;
import cn.gatherlife.basics.service.UserService;
import cn.gatherlife.core.page.PageRequest;
import cn.gatherlife.core.utils.Constant;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

	@Autowired
	private MenuService menuService;
	@Autowired
	private UserRoleMapper userRoleMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private DeptMapper deptMapper;

	@Transactional
	@Override
	public boolean insertOrUpdate(User record) {
		Long id = null;
		if(record.getId() == null || record.getId() == 0) {
			// 新增用户
			this.baseMapper.insert(record);
			id = record.getId();
		} else {
			// 更新用户信息
			this.baseMapper.update(record,
					new EntityWrapper<User>().eq("id", record.getId()));
		}
		// 更新用户角色
		if(id != null && id == 0) {
			return true;
		}
		if(id != null) {
			for(UserRole userRole:record.getUserRoles()) {
				userRole.setUserId(id);
			}
		} else {
			userRoleMapper.delete(new EntityWrapper<UserRole>().eq("user_id", record.getId()));
		}
		for(UserRole userRole:record.getUserRoles()) {
			userRoleMapper.insert(userRole);
		}
		return true;
	}

	/*@Override
	public int delete(User record) {
		return this.baseMapper.deleteById(record.getId());
	}

	@Override
	public int delete(List<User> records) {
		for(User record:records) {
			delete(record);
		}
		return 1;
	}

	@Override
	public User selectById(Long id) {
		return this.baseMapper.selectById(id);
	}*/

    @Override
    public User findByTokenUserId(Long userId){
        return this.baseMapper.selectById(userId);
    }
	
	@Override
	public User findByUsername(String username) {
		User user = new User();
		user.setUsername(username);
		return this.baseMapper.selectOne(user);
	}
	
	@Override
	public Page selectPage(PageRequest pageRequest) {
		String name = pageRequest.getColumnFilter("name").getValue();
		String email = pageRequest.getColumnFilter("email").getValue();

		Page page = new Page();
		page.setCurrent(pageRequest.getPageNum());
		page.setSize(pageRequest.getPageSize());

		EntityWrapper<User> entityWrapper = new EntityWrapper<User>();
		entityWrapper.like(StringUtils.isNotBlank(name), "name", name)
				.like(StringUtils.isNotBlank(email), "email", email)
				.addFilterIfNeed(pageRequest.getColumnFilters().get(Constant.SQL_FILTER) != null,
						pageRequest.getColumnFilters().get(Constant.SQL_FILTER));
		List<User> users = this.baseMapper.selectPage(page, entityWrapper);
		// 加载用户角色信息
		findUserRoles(users);
		page.setRecords(users);
		return page;
	}
	
	/**
	 * 加载用户角色
	 * @param users
	 */
	private void findUserRoles(List<User> users) {
		//List<?> content = pageResult.getRecords();
		for(User user: users) {
			//User user = (User) object;
			List<UserRole> userRoles = findUserRoles(user.getId());
			user.setUserRoles(userRoles);
			user.setRoleNames(getRoleNames(userRoles));
		}
	}

	private String getRoleNames(List<UserRole> userRoles) {
		StringBuilder sb = new StringBuilder();
		for(Iterator<UserRole> iter = userRoles.iterator(); iter.hasNext();) {
			UserRole userRole = iter.next();
			Role role = roleMapper.selectById(userRole.getRoleId());
			if(role == null) {
				continue ;
			}
			sb.append(role.getRemark());
			if(iter.hasNext()) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}

	@Override
	public Set<String> findPermissions(String userName) {	
		Set<String> perms = new HashSet<>();
		List<Menu> menus = menuService.findByUser(userName);
		for(Menu menu:menus) {
			perms.add(menu.getPerms());
		}
		return perms;
	}

	@Override
	public List<UserRole> findUserRoles(Long userId) {
		return userRoleMapper.selectList(new EntityWrapper<UserRole>().eq("user_id", userId));
	}
}
