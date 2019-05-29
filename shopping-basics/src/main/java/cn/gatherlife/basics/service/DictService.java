package cn.gatherlife.basics.service;

import cn.gatherlife.basics.model.Dict;
import cn.gatherlife.core.page.PageRequest;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * 字典管理
 * @author Weir
 * @date Oct 29, 2018
 */
public interface DictService extends IService<Dict> {

	/**
	 * 根据名称查询
	 * @param lable
	 * @return
	 */
	List<Dict> findByLable(String lable);

	/**
	 *分页查询
	 * @param pageRequest
	 * @return
	 */
	Page<Dict> selectPage(PageRequest pageRequest);
}
