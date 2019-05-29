package cn.gatherlife.basics.service;

import cn.gatherlife.basics.model.Dept;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * 机构管理
 * @author Weir
 * @date Oct 29, 2018
 */
public interface DeptService extends IService<Dept> {

	/**
	 * 查询机构树
	 * @return
	 */
	List<Dept> findTree();

}
