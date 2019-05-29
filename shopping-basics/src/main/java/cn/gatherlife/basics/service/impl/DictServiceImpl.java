package cn.gatherlife.basics.service.impl;

import cn.gatherlife.basics.mapper.DictMapper;
import cn.gatherlife.basics.model.Dict;
import cn.gatherlife.basics.service.DictService;
import cn.gatherlife.core.page.PageRequest;
import cn.gatherlife.core.utils.Constant;
import cn.gatherlife.core.utils.Query;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

	/*@Override
	public int insert(Dict record) {
		if(record.getId() == null || record.getId() == 0) {
			return this.baseMapper.insert(record);
		}
		return this.baseMapper.update(record,
				new EntityWrapper<Dict>().eq("id", record.getId()));
	}

	@Override
	public int delete(Dict record) {
		return this.baseMapper.deleteById(record.getId());
	}

	@Override
	public int delete(List<Dict> records) {
		for(Dict record:records) {
			delete(record);
		}
		return 1;
	}

	@Override
	public Dict selectById(Long id) {
		return this.baseMapper.selectById(id);
	}*/

	@Override
	public Page selectPage(PageRequest pageRequest) {
		String label = pageRequest.getColumnFilter("label").getValue();

		Page<Dict> page = this.selectPage(
				new Query<Dict>(pageRequest).getPage(),
				new EntityWrapper<Dict>()
						.like(StringUtils.isNotBlank(label), "label", label)
						.addFilterIfNeed(pageRequest.getColumnFilters().get(Constant.SQL_FILTER) != null,
								pageRequest.getColumnFilters().get(Constant.SQL_FILTER))
		);
		return page;
	}

	@Override
	public List<Dict> findByLable(String lable) {
		return this.baseMapper.selectList(new EntityWrapper<Dict>().eq("lable", lable));
	}

}
