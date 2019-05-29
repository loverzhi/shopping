package cn.gatherlife.basics.service.impl;

import cn.gatherlife.basics.mapper.DeptMapper;
import cn.gatherlife.basics.model.Dept;
import cn.gatherlife.basics.service.DeptService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

	@Override
	public List<Dept> findTree() {
		List<Dept> sysDepts = new ArrayList<>();
		List<Dept> depts = this.baseMapper.selectList(new EntityWrapper<Dept>());
		for (Dept dept : depts) {
			if (dept.getParentId() == null || dept.getParentId() == 0) {
				dept.setLevel(0);
				sysDepts.add(dept);
			}
		}
		findChildren(sysDepts, depts);
		return sysDepts;
	}

	private void findChildren(List<Dept> sysDepts, List<Dept> depts) {
		for (Dept sysDept : sysDepts) {
			List<Dept> children = new ArrayList<>();
			for (Dept dept : depts) {
				if (sysDept.getId() != null && sysDept.getId().equals(dept.getParentId())) {
					dept.setParentName(sysDept.getName());
					dept.setLevel(sysDept.getLevel() + 1);
					children.add(dept);
				}
			}
			sysDept.setChildren(children);
			findChildren(children, depts);
		}
	}

}
