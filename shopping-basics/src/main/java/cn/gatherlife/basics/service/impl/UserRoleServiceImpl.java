package cn.gatherlife.basics.service.impl;

import cn.gatherlife.basics.mapper.UserRoleMapper;
import cn.gatherlife.basics.model.UserRole;
import cn.gatherlife.basics.service.UserRoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

	/*@Override
	public int insert(UserRole record) {
		return 0;
	}

	@Override
	public int delete(UserRole record) {
		return 0;
	}

	@Override
	public int delete(List<UserRole> records) {
		return 0;
	}

	@Override
	public Page selectPage(PageRequest pageRequest) {
		return null;
	}

	@Override
	public UserRole selectById(Long id) {
		return this.baseMapper.selectById(id);
	}
*/

}
